package space_habit_frontier.engine.services.todos;

import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.util.List;
import java.util.UUID;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;

import com.fasterxml.uuid.Generators;

import space_habit_frontier.data_model.db_generated.tables.Todoevents;
import space_habit_frontier.data_model.db_generated.tables.Todos;
import space_habit_frontier.engine.constants.RepeatType;
import space_habit_frontier.engine.dtos.todos.DueDateCalculator;
import space_habit_frontier.engine.dtos.todos.TodoActiveDaysConverters;
import space_habit_frontier.engine.dtos.todos.TodoListDto;
import space_habit_frontier.engine.dtos.user_moves.RollDto;
import space_habit_frontier.engine.dtos.user_moves.UserMoveDto;
import space_habit_frontier.engine.dtos.users.UserDto;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.interfaces.user_moves.UserMoveEventSubscriber;
import space_habit_frontier.engine.interfaces.users.UserProvider;

public class TodoListService implements UserMoveEventSubscriber<TodoListDto> {
	private final DSLContext __context;
	private final UserProvider __userProvider;
	private final DatetimeProvider __datetimeProvider;
	private final TodoEventService __todoEventService;

	public TodoListService(
			DSLContext context,
			UserProvider userProvider,
			DatetimeProvider datetimeProvider,
			TodoEventService todoEventService) {
		__context = context;
		__userProvider = userProvider;
		__datetimeProvider = datetimeProvider;
		__todoEventService = todoEventService;
	}

	private Field<Integer> __todoEventsRowNum() {
		return DSL.rowNumber().over(
			DSL.orderBy(Todoevents.TODOEVENTS.CREATIONDATETIME.desc()))
			.as("row_num");
	}

	private SelectWhereStep<org.jooq.Record> __todosUnfiltered(
			DSLContext ctx,
			UserDto user) {
		var rowNum = __todoEventsRowNum();

		var cte_joinKey = Todoevents.TODOEVENTS.TODOID.as("join_key");

		var res = ctx.with("completed_todos").as(
			ctx.select(
				Todoevents.TODOEVENTS.ID,
				cte_joinKey, 
				Todoevents.TODOEVENTS.CREATIONDATETIME,
				rowNum)
			.from(Todoevents.TODOEVENTS)
			.join(Todos.TODOS)
			.on(Todoevents.TODOEVENTS.TODOID.eq(Todos.TODOS.ID))
			.where(Todos.TODOS.USERID.eq(user.getId()))
			).selectFrom(Todos.TODOS.leftJoin(
				DSL.table(DSL.name("completed_todos")))
				.on(cte_joinKey.eq(Todos.TODOS.ID)));

		return res;
	}

	private TodoListDto __joinedEventRecordToListDto(org.jooq.Record r) {
		return new TodoListDto(
			r.get(Todos.TODOS.ID), 
			r.get(Todos.TODOS.TITLE))
				.setLastCompletedDatetime(
					r.get(Todoevents.TODOEVENTS.CREATIONDATETIME))
				.setRepeatType(
					RepeatType.valueOf(r.get(Todos.TODOS.REPEATTYPE)))
				.setWeekActiveDaysSet(
					TodoActiveDaysConverters
						.weekActiveDaysSet(r.get(Todos.TODOS.WEEKACTIVEDAYS)))
				.setEffectiveDatetime(r.get(Todos.TODOS.EFFECTIVEDATETIME))
				.setCreationDatetime(r.get(Todos.TODOS.CREATIONDATETIME));
	}

	public List<TodoListDto> getTodosActive() {
		var user = __userProvider.getSessionUserRequired();
		return __context.transactionResult(configuration -> {
			var rowNum = __todoEventsRowNum();

			var ctx = configuration.dsl();

			var res = __todosUnfiltered(ctx, user.toUserDto())
				.where(Todos.TODOS.USERID.eq(user.getId()))
				.or(Todos.TODOS.EFFECTIVEDATETIME
					.lessOrEqual(__datetimeProvider.now().toOffsetDateTime())
				.or(Todos.TODOS.EXPIRATIONDATETIMESTAMP
					.greaterOrEqual(__datetimeProvider.now().toOffsetDateTime()))
				.or(Todos.TODOS.EFFECTIVEDATETIME.isNull()))
				.and(rowNum.eq(1).or(rowNum.isNull()))
				.fetch(this::__joinedEventRecordToListDto);
			return res;
		});
	}

	public List<TodoListDto> getTodos() {
		return getTodosActive()
			.stream()
			.filter(t -> {
				if (t.repeatType() == RepeatType.DATE) {
					return t.lastCompletedDatetime().isEmpty();
				}
				//filter out todos that are have been completed today.
				var today = __datetimeProvider.now().toLocalDate();
				return !t.alignLastCompletedDate().isEqual(today);
			})
			.map(this::__setCalculatedDates)
			.toList();
	}

	private TodoListDto __setCalculatedDates(
			TodoListDto todo,
			LocalDateTime checkinDate) {
		var previousCompletion = todo.lastCompletedDatetime()
					.orElse(todo.weekActiveDays().minActiveDate().atTime(OffsetTime.MIN))
					.toLocalDateTime();
		var calculator = new DueDateCalculator(
			todo.weekActiveDays(),
			previousCompletion);
		var dueDate = calculator
			.calculateNextDueDate(checkinDate);
		return todo.setNextDueDate(dueDate);
	}

	private TodoListDto __setCalculatedDates(TodoListDto todo) {
		return __setCalculatedDates(
			todo, 
			__datetimeProvider.now().toLocalDateTime());
	}

	@Override
	public UserMoveDto<TodoListDto> onCompleted(
			UserMoveDto<TodoListDto> userMoveDto) {
		var user = __userProvider.getSessionUserRequired();
		var entity =  __context.transactionResult(configuration -> {
				var ctx = configuration.dsl();
				__todoEventService.addTodoCompletedEvent(userMoveDto.entity(), ctx);

			var res = __todosUnfiltered(ctx, user.toUserDto())
						.where(Todos.TODOS.ID.eq(userMoveDto.entity().id()))
						.fetchOne(this::__joinedEventRecordToListDto);
				return __setCalculatedDates(
					res,
					__datetimeProvider.now().toLocalDateTime());
		});
		return userMoveDto.setEntity(entity);
	}

	@Override
	public UserMoveDto<TodoListDto> onReverted(
			UserMoveDto<TodoListDto> userMoveResult) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'onTodoReverted'");
	}

}

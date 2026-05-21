package space_habit_frontier.engine.services.todos;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
import space_habit_frontier.engine.dtos.todos.TodoFormDto;
import space_habit_frontier.engine.dtos.todos.TodoListDto;
import space_habit_frontier.engine.dtos.users.UserDto;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.interfaces.db.DataContextProvider;
import space_habit_frontier.engine.interfaces.users.UserProvider;

public class TodoService {
	
	private final DSLContext __context;
	private final UserProvider __userProvider;
	private final DatetimeProvider __datetimeProvider;

	public TodoService(
			DataContextProvider dataContextProvider,
			UserProvider userProvider,
			DatetimeProvider datetimeProvider) throws SQLException {
		__context = dataContextProvider.getContext();
		__userProvider = userProvider;
		__datetimeProvider = datetimeProvider;
	}

	public Optional<TodoFormDto> get(UUID id) {
		return __context.transactionResult(configuration -> {
			var ctx = configuration.dsl();
			return ctx.selectFrom(Todos.TODOS)
				.where(Todos.TODOS.ID.eq(id))
				.fetchOptional(r -> new TodoFormDto(id, r.getTitle())
					.setNote(r.getNote())
					.setRisk(r.getRisk())
					.setDuedatetimestamp(
						r.getDuedatetimestamp() != null 
							? r.getDuedatetimestamp().toZonedDateTime() 
							: null)
					.setEffectivedatetimestamp(
						r.getEffectivedatetimestamp() != null 
							? r.getEffectivedatetimestamp().toZonedDateTime() 
							: null)
					.setRepeatcount(r.getRepeatcount())
					.setRepeattype(r.getRepeattype())
					.setRepeatrate(r.getRepeatrate())
					.setWeekactivedays(
						TodoActiveDaysConverters.
						getWeekActiveDaysNames(r.getWeekactivedays()))
					.setYearactivedays(
						TodoActiveDaysConverters
						.getYearActivedaysMonthDayList(
							Arrays.asList(r.getYearactivedays())))
					.setMonthactivedays(r.getMonthactivedays())
					.setPoisonous(r.getPoisonous())
					.setExpirationdatetimestamp(
						r.getExpirationdatetimestamp() != null 
							? r.getExpirationdatetimestamp().toZonedDateTime() 
							: null)
					.setRateinversionflag(r.getRateinversionflag())
				);
		});
	}

	public TodoListDto add(TodoFormDto formDto) {
		var id = Generators.timeBasedEpochRandomGenerator().generate();
		var timestamp = __datetimeProvider.now().toOffsetDateTime();
		var userId = __userProvider.getSessionUserRequired().getId();
		__context.transaction(configuration -> {
			var ctx = configuration.dsl();
			ctx.insertInto(Todos.TODOS)
				.set(Todos.TODOS.ID, id)
				.set(Todos.TODOS.TITLE, formDto.getTitle())						
				.set(Todos.TODOS.STREAKSTARTTIMESTAMP, timestamp)
				.set(Todos.TODOS.CREATIONTIMESTAMP, timestamp)
				.set(Todos.TODOS.USERID, userId)
				.execute();
		});
		return new TodoListDto(id, formDto.getTitle());
	}

	public TodoFormDto update(UUID id, TodoFormDto formDto) {
		__context.transaction(configuration -> {
			var ctx = configuration.dsl();
			ctx.update(Todos.TODOS)
				.set(Todos.TODOS.TITLE, formDto.getTitle())
				.set(Todos.TODOS.NOTE, formDto.getNote())
				.set(Todos.TODOS.RISK, formDto.getRisk())
				.set(
					Todos.TODOS.DUEDATETIMESTAMP, 
					formDto.getDuedatetimestamp() != null 
						? formDto.getDuedatetimestamp().toOffsetDateTime()
						: null)
				.set(
					Todos.TODOS.EFFECTIVEDATETIMESTAMP,
					formDto.getEffectivedatetimestamp() != null
						? formDto.getEffectivedatetimestamp().toOffsetDateTime()
						: null)
				.set(Todos.TODOS.REPEATCOUNT, formDto.getRepeatcount())
				.set(Todos.TODOS.REPEATTYPE, formDto.getRepeattype())
				.set(Todos.TODOS.REPEATRATE, formDto.getRepeatrate())
				.set(Todos.TODOS.WEEKACTIVEDAYS, formDto.getWeekActivedaysByteString())
				.set(
					Todos.TODOS.YEARACTIVEDAYS,
					formDto.getYearActivedaysIntegerArray())
				.set(
						Todos.TODOS.MONTHACTIVEDAYS,
						formDto.getMonthactivedays())
				.set(
					Todos.TODOS.POISONOUS,
					formDto.isPoisonous())
				.set(Todos.TODOS.EXPIRATIONDATETIMESTAMP,
					formDto.getExpirationdatetimestamp() != null 
						? formDto.getExpirationdatetimestamp().toOffsetDateTime()
						: null)
				.set(
					Todos.TODOS.RATEINVERSIONFLAG,
					formDto.isRateinversionflag())
				.where(Todos.TODOS.ID.eq(id))
				.execute();
		});
		return formDto; 
	}

	private Field<Integer> __todoEventsRowNum() {
		return DSL.rowNumber().over(
			DSL.orderBy(Todoevents.TODOEVENTS.CREATIONTIMESTAMP.desc()))
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
				Todoevents.TODOEVENTS.CREATIONTIMESTAMP,
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
					r.get(Todoevents.TODOEVENTS.CREATIONTIMESTAMP))
				.setCycleRateType(
					RepeatType.valueOf(r.get(Todos.TODOS.REPEATTYPE)))
				.setWeekActiveDaysSet(
					TodoActiveDaysConverters
						.weekActiveDaysSet(r.get(Todos.TODOS.WEEKACTIVEDAYS)));
	}

	public List<TodoListDto> getTodosActive() {
		var user = __userProvider.getSessionUserRequired();
		return __context.transactionResult(configuration -> {
			var rowNum = __todoEventsRowNum();

			var ctx = configuration.dsl();

			var res = __todosUnfiltered(ctx, user.toUserDto())
				.where(Todos.TODOS.USERID.eq(user.getId()))
				.or(Todos.TODOS.EFFECTIVEDATETIMESTAMP
					.lessOrEqual(__datetimeProvider.now().toOffsetDateTime())
				.or(Todos.TODOS.EXPIRATIONDATETIMESTAMP
					.greaterOrEqual(__datetimeProvider.now().toOffsetDateTime()))
				.or(Todos.TODOS.EFFECTIVEDATETIMESTAMP.isNull()))
				.and(rowNum.eq(1).or(rowNum.isNull()))
				.fetch(this::__joinedEventRecordToListDto);
			return res;
		});
	}

	public List<TodoListDto> getTodos() {

		return getTodosActive()
			.stream()
			.filter(t -> {
				if (t.cycleRateType() == RepeatType.DATE) {
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

	public TodoListDto completeTodo(UUID todoId) {
			var user = __userProvider.getSessionUserRequired();
			return __context.transactionResult(configuration -> {
				var ctx = configuration.dsl();
				ctx.insertInto(Todoevents.TODOEVENTS)
					.set(
						Todoevents.TODOEVENTS.ID,
						Generators.timeBasedEpochRandomGenerator().generate())
					.set(Todoevents.TODOEVENTS.TODOID, todoId)
					.set(Todoevents.TODOEVENTS.USERID, user.getId())
					.set(
						Todoevents.TODOEVENTS.CREATIONTIMESTAMP,
						__datetimeProvider.now().toOffsetDateTime())
					.execute();

				var res = __todosUnfiltered(ctx, user.toUserDto())
						.where(Todos.TODOS.ID.eq(todoId))
						.fetchOne(this::__joinedEventRecordToListDto);
				return __setCalculatedDates(
					res,
					__datetimeProvider.now().toLocalDateTime());
			});
	}
	
}

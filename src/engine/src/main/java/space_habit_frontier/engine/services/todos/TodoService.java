package space_habit_frontier.engine.services.todos;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import com.fasterxml.uuid.Generators;

import space_habit_frontier.data_model.db_generated.tables.Todoevents;
import space_habit_frontier.data_model.db_generated.tables.Todos;
import space_habit_frontier.engine.dtos.todos.TodoFormDto;
import space_habit_frontier.engine.dtos.todos.TodoListDto;
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

	public void add(TodoFormDto formDto) {
		var id = Generators.timeBasedEpochRandomGenerator().generate();
		var timestamp = __datetimeProvider.now().toOffsetDateTime();
		var userId = __userProvider.getSessionUserRequired().getId();
		__context.transaction(configuration -> {
			var ctx = configuration.dsl();
			ctx.insertInto(Todos.TODOS)
				.set(Todos.TODOS.ID, id)
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
						
				.set(Todos.TODOS.STREAKSTARTTIMESTAMP, timestamp)
				.set(Todos.TODOS.REPEATCOUNT, formDto.getRepeatcount())
				.set(Todos.TODOS.REPEATTYPE, formDto.getRepeattype())
				.set(Todos.TODOS.REPEATRATE, formDto.getRepeatrate())
				.set(Todos.TODOS.WEEKACTIVEDAYS, formDto.getWeekactivedaysByteString())
				.set(
					Todos.TODOS.YEARACTIVEDAYS,
					formDto.getYearactivedaysIntegerArray())
				.set(
						Todos.TODOS.MONTHACTIVEDAYS,
						formDto.getMonthactivedays())
				.set(Todos.TODOS.CREATIONTIMESTAMP, timestamp)
				.set(Todos.TODOS.USERID, userId)
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
				.execute();
		});
	}

	public List<TodoListDto> getTodos() {
		var user = __userProvider.getSessionUserRequired();
		return __context.transactionResult(configuration -> {
			var ctx = configuration.dsl();
			var rowNum = DSL.rowNumber().over(
				DSL.orderBy(Todoevents.TODOEVENTS.CREATIONTIMESTAMP.desc()))
				.as("row_num");

			var cte_joinKey = Todoevents.TODOEVENTS.TODOID.as("join_key");

			var res = ctx.with("completed_todos").as(
				ctx.select(
					Todoevents.TODOEVENTS.ID,
					cte_joinKey, 
					Todoevents.TODOEVENTS.CREATIONTIMESTAMP,
					rowNum)
				.from(Todoevents.TODOEVENTS)
				.where(Todos.TODOS.USERID.eq(user.getId()))
				.and(rowNum.eq(1))
				).selectFrom(Todos.TODOS.leftJoin(
					DSL.table(DSL.name("completed_todos")))
					.on(cte_joinKey.eq(Todos.TODOS.ID)))
					.where(Todos.TODOS.USERID.eq(user.getId()))
					.or(Todos.TODOS.EFFECTIVEDATETIMESTAMP
						.lessOrEqual(__datetimeProvider.now().toOffsetDateTime())
					.or(Todos.TODOS.EXPIRATIONDATETIMESTAMP
						.greaterOrEqual(__datetimeProvider.now().toOffsetDateTime()))
					.or(Todos.TODOS.EFFECTIVEDATETIMESTAMP.isNull()))
				.fetch(r -> new TodoListDto(
					r.get(Todos.TODOS.ID), 
					r.get(Todos.TODOS.TITLE)));

			return res;
		});
	}

	public void completeTodo(UUID todoId) {
			// var user = __userProvider.getSessionUserRequired();
			// return __context.transactionResult(configuration -> {
			// 	var res = __context.selectFrom(Todos.TODOS)
			// 		.where(Todos.TODOS.USERID.eq(user.getId()))
			// 		.and(Todos.TODOS.ID.eq(todoId))
			// 		.fetchOne();
				
			// });
	}
	
}

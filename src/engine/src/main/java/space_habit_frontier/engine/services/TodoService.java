package space_habit_frontier.engine.services;

import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;

import com.fasterxml.uuid.Generators;

import space_habit_frontier.engine.db_generated.tables.Todos;
import space_habit_frontier.engine.dtos.TodoFormDto;
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

	public List<String> getTodos() {

		var user = __userProvider.getSessionUserRequired();
		return List.of(user.getUsername());
	}

	public void Add(TodoFormDto formDto) {
		var id = Generators.timeBasedEpochRandomGenerator().generate();
		var timestamp = __datetimeProvider.now().toOffsetDateTime();
		var userId = __userProvider.getSessionUserRequired().getId();
		__context.transaction(configuration -> {
			var ctx = configuration.dsl();
			ctx.insertInto(Todos.TODOS)
				.set(Todos.TODOS.ID, id)
				.set(Todos.TODOS.USERID, userId)
				.set(Todos.TODOS.TITLE, formDto.getTitle())
				.set(Todos.TODOS.NOTE, formDto.getNote())
				.set(Todos.TODOS.RISK, formDto.getRisk())
				.set(
					Todos.TODOS.DUEDATETIMESTAMP, 
					formDto.getDuedatetimestamp().toOffsetDateTime())
				.set(Todos.TODOS.STREAKSTARTTIMESTAMP, timestamp)
				// .set(Todos.TODOS.REPEATCOUNT, formDto)
				.set(Todos.TODOS.CREATIONTIMESTAMP, timestamp)
				.set(Todos.TODOS.WEEKACTIVEDAYS, formDto.getWeekactivedaysByteString())
				.set(
					Todos.TODOS.YEARACTIVEDAYS,
					formDto.getYearactivedaysIntegerArray())
				.execute();
		});
	}
}

package space_habit_frontier.engine.services.todos;

import java.sql.SQLException;
import java.util.UUID;

import org.jooq.DSLContext;

import com.fasterxml.uuid.Generators;

import space_habit_frontier.data_model.db_generated.tables.Todoevents;
import space_habit_frontier.engine.dtos.todos.TodoListDto;
import space_habit_frontier.engine.dtos.user_moves.RollDto;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.interfaces.db.DataContextProvider;
import space_habit_frontier.engine.interfaces.user_moves.UserMoveEventSubscriber; 
import space_habit_frontier.engine.interfaces.users.UserProvider;

public class TodoEventService {
	private final DSLContext __context;
	private final UserProvider __userProvider;
	private final DatetimeProvider __datetimeProvider;

	public TodoEventService(
			DataContextProvider dataContextProvider,
			UserProvider userProvider,
			DatetimeProvider datetimeProvider) throws SQLException {
		__context = dataContextProvider.getContext();
		__userProvider = userProvider;
		__datetimeProvider = datetimeProvider;
	}

	public void addTodoCompletedEvent(TodoListDto todoListDto, DSLContext ctx) {
		var timestamp = __datetimeProvider.now().toOffsetDateTime();
		var id = Generators.timeBasedEpochRandomGenerator().generate();
		ctx.insertInto(Todoevents.TODOEVENTS)
			.set(Todoevents.TODOEVENTS.ID, id)
			.set(Todoevents.TODOEVENTS.TODOID, todoListDto.id())
			.set(Todoevents.TODOEVENTS.DAMAGE, todoListDto.damage())
			.set(Todoevents.TODOEVENTS.CREATIONDATETIME, timestamp)
			.execute();
	}

}

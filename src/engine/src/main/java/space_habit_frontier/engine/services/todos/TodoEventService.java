package space_habit_frontier.engine.services.todos;

import java.sql.SQLException;
import java.util.UUID;

import org.jooq.DSLContext;

import com.fasterxml.uuid.Generators;

import space_habit_frontier.data_model.db_generated.tables.Todoevents;
import space_habit_frontier.engine.dtos.events.RollDto;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.interfaces.db.DataContextProvider;
import space_habit_frontier.engine.interfaces.todos.TodoEventSubscriber;
import space_habit_frontier.engine.interfaces.users.UserProvider;

public class TodoEventService implements TodoEventSubscriber {
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

	public void addTodoCompletedEvent(RollDto roll) {
		var timestamp = __datetimeProvider.now().toOffsetDateTime();
			__context.transaction(configuration -> {
				var ctx = configuration.dsl();
				var id = Generators.timeBasedEpochRandomGenerator().generate();
				ctx.insertInto(Todoevents.TODOEVENTS)
					.set(Todoevents.TODOEVENTS.ID, id)
					.set(Todoevents.TODOEVENTS.TODOID, roll.id())
					.set(Todoevents.TODOEVENTS.DAMAGE, roll.damage())
					.set(Todoevents.TODOEVENTS.CREATIONTIMESTAMP, timestamp)
					.execute();
			});
	}

	@Override
	public void onTodoCompleted(RollDto roll) {
		addTodoCompletedEvent(roll);
	}

	@Override
	public void onTodoReverted(UUID todoId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'onTodoReverted'");
	}


}

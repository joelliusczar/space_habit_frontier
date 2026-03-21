package space_habit_frontier.engine.services;

import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;

import space_habit_frontier.engine.dtos.TodoFormDto;
import space_habit_frontier.engine.interfaces.db.DataContextProvider;
import space_habit_frontier.engine.interfaces.users.UserProvider;

public class TodoService {
	
	private final DSLContext __context;
	private final UserProvider __userProvider;

	public TodoService(
			DataContextProvider dataContextProvider,
			UserProvider userProvider) throws SQLException {
		__context = dataContextProvider.getContext();
		__userProvider = userProvider;
	}

	public List<String> getTodos() {

		var user = __userProvider.getSessionUserRequired();
		return List.of(user.getUsername());
	}

	// public TodoFormDto Add(TodoFormDto formDto) {

	// }
}

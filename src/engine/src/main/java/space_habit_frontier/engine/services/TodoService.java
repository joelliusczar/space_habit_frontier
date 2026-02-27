package space_habit_frontier.engine.services;

import java.sql.SQLException;

import org.jooq.DSLContext;

import space_habit_frontier.engine.dtos.TodoFormDto;
import space_habit_frontier.engine.interfaces.db.DataContextProvider;

public class TodoService {
	
	private DSLContext __context;

	public TodoService(DataContextProvider dataContextProvider) throws SQLException {
		__context = dataContextProvider.getContext();
	}

	// public TodoFormDto Add(TodoFormDto formDto) {

	// }
}

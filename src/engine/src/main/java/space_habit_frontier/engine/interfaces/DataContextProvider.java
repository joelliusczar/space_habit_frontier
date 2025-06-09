package space_habit_frontier.engine.interfaces;

import java.sql.SQLException;

import org.jooq.DSLContext;


public interface DataContextProvider {
	
	DSLContext getContext() throws SQLException;
	
}

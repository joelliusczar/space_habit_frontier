package space_habit_frontier.engine.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {
	
	Connection getDbConnection() throws SQLException;

}

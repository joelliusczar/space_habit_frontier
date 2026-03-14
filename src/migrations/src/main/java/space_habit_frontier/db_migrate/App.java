package space_habit_frontier.db_migrate;

import org.flywaydb.core.Flyway;

public class App {
	public static void main(String[] args) {
		// Run the Flyway migration to apply any pending database migrations.
		var flyway = Flyway.configure()
			.validateMigrationNaming(true)
			.dataSource("jdbc:postgresql://localhost:5432/space_habit_frontier_db",
				System.getenv("DSF_DB_OWNER_NAME"), 
				System.getenv("DSF_DB_OWNER_PASS"))
			.load();
		flyway.migrate();
	}
}

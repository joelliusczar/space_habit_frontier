package space_habit_frontier.engine.services.environment;

public class EnvironmentWrapper {
	
	public static String getEventLogDirectory() {
		return System.getenv("DSF_EVENT_LOGS_DIR");
	}

	public static String getVisitLogDirectory() {
		return System.getenv("DSF_VISIT_LOGS_DIR");
	}
}

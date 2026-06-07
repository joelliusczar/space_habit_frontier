package space_habit_frontier.engine.constants;

public enum TodoEventType {
	BLANK(""),
	ACTIVE("ACTIVE");

	private String __key;

	private TodoEventType(String key) {
		this.__key = key;
	}

	public String key() {
		return __key;
	}
}

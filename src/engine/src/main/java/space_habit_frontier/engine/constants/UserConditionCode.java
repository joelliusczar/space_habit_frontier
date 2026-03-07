package space_habit_frontier.engine.constants;

public enum UserConditionCode {
	ACTIVE(0),
	DISABLED(1),
	OUTDATEDPASSWORD(2);

	private int __conditionCode;

	private UserConditionCode(int code) {
		this.__conditionCode = code;
	}

	public int getConditionCode() {
		return __conditionCode;
	}

}

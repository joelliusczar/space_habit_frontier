
package space_habit_frontier.engine.constants;

import space_habit_frontier.engine.interfaces.FriendlyNameable;

import org.apache.commons.text.WordUtils;

public enum RepeatType implements FriendlyNameable {
	DATE(0),
	DAILY(1),
	WEEKLY(7),
	MONTHLY(31),
	YEARLY(366);
	
	private int __repeattype;
	
	RepeatType(int repeattype) {
		this.__repeattype = repeattype;
	}
	
	public int getValue() {
		return __repeattype;
	}

	public String getFriendlyName() {
		return WordUtils.capitalizeFully(this.name());
	}

	public static RepeatType valueOf(int value) {
		return RepeatType.values()[value];
	}
}
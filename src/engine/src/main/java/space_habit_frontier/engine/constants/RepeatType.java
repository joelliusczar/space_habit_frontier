
package space_habit_frontier.engine.constants;

import space_habit_frontier.engine.interfaces.FriendlyNameable;

import org.apache.commons.text.WordUtils;

public enum RepeatType implements FriendlyNameable {
	DATE((short)0),
	DAILY((short)1),
	WEEKLY((short)7),
	MONTHLY((short)31),
	YEARLY((short)366);
	
	private short __repeattype;
	
	RepeatType(short repeattype) {
		this.__repeattype = repeattype;
	}
	
	public short getValue() {
		return __repeattype;
	}

	public String getFriendlyName() {
		return WordUtils.capitalizeFully(this.name());
	}

	public static RepeatType valueOf(short value) {
		return RepeatType.values()[value];
	}
}
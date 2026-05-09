
package space_habit_frontier.engine.constants;

import space_habit_frontier.engine.interfaces.FriendlyNameable;

import org.apache.commons.text.WordUtils;

public enum CycleRateType implements FriendlyNameable {
	DATE,
	DAILY,
	WEEKLY,
	MONTHLY,
	YEARLY;

	public String getFriendlyName() {
		return WordUtils.capitalizeFully(this.name());
	}

	public static CycleRateType valueOf(int value) {
		return CycleRateType.values()[value];
	}
}
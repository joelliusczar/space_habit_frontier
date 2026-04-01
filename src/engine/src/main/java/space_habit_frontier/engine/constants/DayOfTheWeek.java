package space_habit_frontier.engine.constants;

import org.apache.commons.text.WordUtils;

import space_habit_frontier.engine.interfaces.FriendlyNameable;

public enum DayOfTheWeek implements FriendlyNameable {
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY,
	SUNDAY;

	public String getFriendlyName() {
		return WordUtils.capitalizeFully(this.name());
	}

}

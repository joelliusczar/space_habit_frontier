package space_habit_frontier.engine.constants;

import space_habit_frontier.engine.interfaces.FriendlyNameable;

import org.apache.commons.text.WordUtils;

enum DayOfWeek implements FriendlyNameable {
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
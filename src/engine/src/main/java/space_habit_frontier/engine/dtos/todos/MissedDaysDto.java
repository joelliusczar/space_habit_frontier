package space_habit_frontier.engine.dtos.todos;


public record MissedDaysDto(
	long firstPartialPeriodCount,
	long fullPeriodCount,
	long lastPartialPeriodCount) {}

package space_habit_frontier.engine.services.todos;

public record RateValueItem (
	boolean isDayActive, long backRange, long forwardRange) {
}


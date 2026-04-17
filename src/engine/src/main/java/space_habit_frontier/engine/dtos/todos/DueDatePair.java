package space_habit_frontier.engine.dtos.todos;

import java.time.ZonedDateTime;

public record DueDatePair(ZonedDateTime previous, ZonedDateTime next) {
	
}

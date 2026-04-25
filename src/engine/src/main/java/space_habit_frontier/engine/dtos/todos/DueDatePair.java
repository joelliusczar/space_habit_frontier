package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDateTime;


public record DueDatePair(LocalDateTime previous, LocalDateTime next) {
	
}

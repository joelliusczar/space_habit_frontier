package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDate;
import java.util.Optional;


public record DueDatePair(Optional<LocalDate> previous, LocalDate next) {
	
}

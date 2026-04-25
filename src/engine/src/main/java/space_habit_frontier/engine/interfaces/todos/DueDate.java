package space_habit_frontier.engine.interfaces.todos;

import java.time.LocalDateTime;

public interface DueDate {
	LocalDateTime calculateNextDueDate(LocalDateTime checkinDate);
	boolean isDateADueDate(LocalDateTime checkinDate);
}

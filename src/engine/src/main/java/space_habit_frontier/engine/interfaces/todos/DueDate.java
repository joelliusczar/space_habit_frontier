package space_habit_frontier.engine.interfaces.todos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import space_habit_frontier.engine.dtos.todos.TodoListDto;

public interface DueDate {
	LocalDate calculateNextDueDate(LocalDateTime checkinDate);
	LocalDate calculateNextDueDate(OffsetDateTime checkinDate, TodoListDto todo);
	boolean isDateADueDate(LocalDateTime checkinDate);
	boolean isDateADueDate(OffsetDateTime checkinDate, TodoListDto todo);
}

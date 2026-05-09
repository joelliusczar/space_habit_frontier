package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface DateAligner {

	LocalTime dayStartHour();

	DateAligner setDayStartHour(LocalTime dayStartHour);

	default LocalDate alignDate(LocalDateTime date) {
		if(date.toLocalTime().isBefore(dayStartHour()) 
				|| date.toLocalTime().equals(dayStartHour())) {
			return date.toLocalDate();
		}
		return date.minusDays(1).toLocalDate();
	}
}

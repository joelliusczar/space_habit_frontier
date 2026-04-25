package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDateTime;

public class DueDateShared {
	
	public static LocalDateTime prepareDateForCalculations(
			LocalDateTime date,
			int dayStartHour) {
		var offsetTime = date.withHour(dayStartHour)
				.withMinute(0)
				.withSecond(0)
				.withNano(0).toLocalTime();
		if(date.toLocalTime().isBefore(offsetTime) 
				|| date.toLocalTime().equals(offsetTime)) {
			return date.withHour(dayStartHour)
				.withMinute(0)
				.withSecond(0)
				.withNano(0);
		}
		return date
			.withHour(0)
			.withMinute(0)
			.withSecond(0)
			.withNano(0)
			.minusDays(1);
	}
}

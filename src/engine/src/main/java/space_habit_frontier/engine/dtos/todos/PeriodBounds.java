package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDate;

public record PeriodBounds(
	LocalDate periodStart,
	LocalDate nextPeriodStart) {
		
		public boolean isWithinPeriod(LocalDate date) {
			return (periodStart().isBefore(date) || periodStart().equals(date))
				&& nextPeriodStart().isAfter(date);
		}
	}
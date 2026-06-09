package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDate;

public record PeriodBounds(
	LocalDate periodStart,
	LocalDate nextPeriodStart) {
		
		public boolean isWithinPeriod(LocalDate date) {
			return (periodStart().isBefore(date) || periodStart().equals(date))
				&& nextPeriodStart().isAfter(date);
		}

		public static PeriodBounds minSort(LocalDate a, LocalDate b) {
			return a.isBefore(b) ? new PeriodBounds(a, b) : new PeriodBounds(b, a);
		}
	}
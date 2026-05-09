package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDateTime;
import java.util.Set;

import space_habit_frontier.engine.constants.CycleRateType;

public class RepeatingDueDateCalculatorFactory {
	public static DueDateCalculator build(
			CycleRateType cycleRateType,
			Set<Integer> weekActiveDaysSet,
			LocalDateTime lastCompletedDatetime) {
		return switch (cycleRateType) {
			case CycleRateType.WEEKLY -> 
				new WeeklyDueDate(weekActiveDaysSet, lastCompletedDatetime);
			default -> 
				throw new IllegalArgumentException(
					"Unsupported cycle rate type: " + cycleRateType);
		};
	}
}

package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDateTime;
import java.util.Set;

import space_habit_frontier.engine.interfaces.todos.DueDate;

public class MonthlyDueDate implements DueDate{

	private LocalDateTime __previousCheckinDate;
	private Set<Integer> __activeDays;
	private int __intervalSize;
	private int __dayStartHour;

	public MonthlyDueDate(
		Set<Integer> activeDays,
		LocalDateTime previousCheckinDate) {
		__activeDays = activeDays;
		__intervalSize = 1;
		__dayStartHour = 0;
	}



	@Override
	public LocalDateTime calculateNextDueDate(LocalDateTime checkinDate) {

		var previousCheckinDatePrepared = DueDateShared.prepareDateForCalculations(
			__previousCheckinDate,
			__dayStartHour);
		var checkinDatePrepared = DueDateShared.prepareDateForCalculations(
			checkinDate,
			__dayStartHour);

		if (previousCheckinDatePrepared.isAfter(checkinDatePrepared) 
				|| previousCheckinDatePrepared.isEqual(checkinDatePrepared)) {
			throw new IllegalArgumentException(
				"Checkin date should be after previous checkin date");
		}

		var dayOfMonth = checkinDate.getDayOfMonth();

		if (!__activeDays.contains(dayOfMonth)) {
			throw new IllegalArgumentException(
				"Check-in date's day of month is not in active days.");
		}

		var previousCheckinMonth = __previousCheckinDate.getMonthValue();

	}

	@Override
	public boolean isDateADueDate(LocalDateTime checkinDate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isDateADueDate'");
	}
	
}

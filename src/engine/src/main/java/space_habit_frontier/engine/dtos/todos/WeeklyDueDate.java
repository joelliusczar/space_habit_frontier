package space_habit_frontier.engine.dtos.todos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Set;


public class WeeklyDueDate extends DueDateCalculator{

	public static final int WEEK_LEN = 7;

	public WeeklyDueDate(
		Set<Integer> activeDays,
		LocalDateTime previousCheckinDate) {
		super(activeDays, previousCheckinDate);
	}

	@Override
	protected int periodLength() {
		return WEEK_LEN;
	}

	@Override
	protected int dayOfPeriod(LocalDateTime date) {
		return idx(date.getDayOfWeek());
	}

	@Override
	protected int dayOfPeriod(LocalDate date) {
		return idx(date.getDayOfWeek());
	}

	@Override
	public long periodsBetween(
		Temporal inclusive,
		Temporal exclusive) {
			return ChronoUnit.WEEKS.between(
				inclusive,
				exclusive);
	}

	public static int idx(DayOfWeek day) {
		return day.getValue() % WEEK_LEN;
	}	

}

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
		Set<Integer> activeDaysMap,
		LocalDateTime previousCheckinDate) {
		super(activeDaysMap, previousCheckinDate);
	}

	@Override
	protected int periodLength() {
		return WEEK_LEN;
	}

	@Override
	protected int dayOfPeriod(LocalDateTime date, int offset) {
		return idx(date.getDayOfWeek(), offset);
	}

	@Override
	protected int dayOfPeriod(LocalDateTime date) {
		return dayOfPeriod(date, periodOffset());
	}

	@Override
	protected int dayOfPeriod(LocalDate date, int offset) {
		return idx(date.getDayOfWeek(), offset);
	}

	@Override
	protected int dayOfPeriod(LocalDate date) {
		return dayOfPeriod(date, periodOffset());
	}

	@Override
	public long periodsBetween(
		Temporal inclusive,
		Temporal exclusive) {
			return ChronoUnit.WEEKS.between(
				inclusive,
				exclusive);
	}

	public static int idx(DayOfWeek dayOfWeek, int offset) {
		var day = dayOfWeek.getValue() % WEEK_LEN;
		return (day + WEEK_LEN - offset) % WEEK_LEN;
	}

	public static int idx(DayOfWeek day) {
		return idx(day, 0);
	}	

}

package space_habit_frontier.engine.dtos.todos.active_days;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import space_habit_frontier.engine.constants.RepeatType;

public class WeeklyActiveDaysCollection extends ActiveDaysCollection {
	public WeeklyActiveDaysCollection(Collection<Integer> store) {
		super(store);
	}

	public WeeklyActiveDaysCollection(DayOfWeek... store) {
		super(Set
			.of(store)
			.stream()
			.map(WeeklyActiveDaysCollection::idx)
			.collect(Collectors.toSet()));
	}

	public static WeeklyActiveDaysCollection fullWeek() {
		return new WeeklyActiveDaysCollection(
			DayOfWeek.values()
		);
	}

	@Override
	public int periodLength() {
		return RepeatType.WEEKLY.getValue();
	}

	@Override
	public int dayOfPeriod(LocalDate date) {
		return idx(date.getDayOfWeek());
	}

	@Override
	public int dayOfPeriod(LocalDateTime date) {
		return idx(date.getDayOfWeek());
	}

	@Override
	public long periodsBetween(Temporal inclusive, Temporal exclusive) {
		return ChronoUnit.WEEKS.between(
			inclusive,
			exclusive);
	}

	public static int idx(DayOfWeek day) {
		return day.getValue() % RepeatType.WEEKLY.getValue();
	}

	public LocalDate minActiveDate() {
		var candidate = LocalDate.MIN.withYear(2000);
		for (int i = 0; i < RepeatType.WEEKLY.getValue(); i++) {
			if (canDayBeActive(candidate)) {
				return candidate;
			}
			candidate = candidate.plusDays(1);
		}
		throw new RuntimeException("Unable to find minimum active date");
	}
	
}

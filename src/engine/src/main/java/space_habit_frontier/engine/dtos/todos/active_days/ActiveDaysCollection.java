package space_habit_frontier.engine.dtos.todos.active_days;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class ActiveDaysCollection {
	private Collection<Integer> __store;

	public ActiveDaysCollection(Collection<Integer> store) {
		this.__store = store;
	}

	public Collection<Integer> store() {
		return __store;
	}

	public ActiveDaysCollection setStore(Collection<Integer> store) {
		this.__store = store;
		return this;
	}

	public boolean contains(int day) {
		return __store.contains(day);
	}

	public boolean isEmpty() {
		return __store.isEmpty();
	}

	public Stream<Integer> stream() {
		return __store.stream();
	}

	public int size() {
		return __store.size();
	}

	public abstract int periodLength();

	public abstract int dayOfPeriod(LocalDate date);

	public abstract int dayOfPeriod(LocalDateTime date);

	public abstract long periodsBetween(
		Temporal inclusive,
		Temporal exclusive);

	public int findPrevDayOfPeriod(
			int checkinDay,
			boolean isActivePeriod) {
		var previousDay = 0;

		for (int i = 0; i < periodLength(); i++) {
			if (isActivePeriod) {
				var dayValue = ((periodLength() + checkinDay - i - 1) % periodLength());
				
				if (canDayBeActive(dayValue)) {
					return dayValue;
				}
			}
			else {
				var day = periodLength() - i - 1;
				if (canDayBeActive(day)) {
					return day;
				}
			}
		}
		return previousDay;
	}

	public Optional<LocalDate> nextActiveDay(LocalDate candidate) {
		for (int i = 0; i < periodLength(); i++) {
			if (canDayBeActive(candidate)) {
				return Optional.of(candidate);
			}
			candidate = candidate.plusDays(1);
		}

		return Optional.empty();
	}

	public Optional<OffsetDateTime> nextActiveDay(OffsetDateTime candidate) {
		var localDate = candidate.toLocalDate();
		var tempResult = nextActiveDay(localDate);
		if (tempResult.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(
			OffsetDateTime.of(
				tempResult.get(),
				candidate.toLocalTime(),
				candidate.getOffset()));
	}

	public int findNextDayOfPeriod(int checkinDay) {
		for (int i = 0; i < periodLength(); i++) {
			var day = (periodLength() + checkinDay + i) % periodLength();
			if (canDayBeActive(day)) {
				return day;
			}
		}
		throw new IllegalStateException("At least one day should be active");
	}

	public int offsetForSamePeriod(
			int checkinDay,
			int prevDay,
			boolean isActiveWeek) {
		/*
		if checkin day is in active week but before all active days
		push it back a week so that it get's the last active day of
		the previous active weeks
	 */
		return (prevDay % periodLength()) 
			> (checkinDay % periodLength())
				|| (checkinDay == prevDay && isActiveWeek)
			? periodLength() 
			: 0;
	}

	public long distanceFromActivePeriod(long periodNum, int periodScaler) {
		return periodNum % (periodScaler * periodLength());
	}

	public boolean canDayBeActive(int day) {
		return store().contains(day);
	}

	public boolean canDayBeActive(LocalDate date) {
		return canDayBeActive(dayOfPeriod(date));
	}

	public boolean canDayBeActive(LocalDateTime date) {
		return canDayBeActive(dayOfPeriod(date));
	}
}

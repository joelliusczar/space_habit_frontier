package space_habit_frontier.engine.dtos.todos;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import space_habit_frontier.engine.interfaces.todos.DueDate;

public class WeeklyDueDate implements DueDate {
	private Set<DayOfWeek> __activeDaysMap;
	private LocalDateTime __previousCheckinDate;
	private long __intervalSize;
	private int __dayStartHour;
	private DayOfWeek __weekStartDay;

	public static final short WEEK_LENGTH = 7;

	public WeeklyDueDate(
		Set<DayOfWeek> activeDaysMap,
		LocalDateTime previousCheckinDate) {
		this.__activeDaysMap = activeDaysMap;
		this.__previousCheckinDate = previousCheckinDate;
		this.__intervalSize = 1;
		this.__dayStartHour = 0;
		this.__weekStartDay = DayOfWeek.SUNDAY;
	}

	public LocalDateTime getPreviousCheckinDate() {
		return __previousCheckinDate;
	}

	public WeeklyDueDate setPreviousCheckinDate(
			LocalDateTime previousCheckinDate) {
		this.__previousCheckinDate = previousCheckinDate;
		return this;
	}


	public Set<DayOfWeek> getActiveDaysMap() {
		return __activeDaysMap;
	}

	public WeeklyDueDate setActiveDaysMap(Set<DayOfWeek> activeDays) {
		if (activeDays.isEmpty()) {
			throw new IllegalArgumentException(
				"Active days must have at least one element.");
		}
		this.__activeDaysMap = activeDays;
		return this;
	}

	public long getIntervalSize() {
		return __intervalSize;
	}

	public WeeklyDueDate setIntervalSize(long intervalSize) {
		if (intervalSize < 1) {
			throw new IllegalArgumentException("intervalSize must be at least 1");
		}
		this.__intervalSize = intervalSize;
		return this;
	}

	public int getDayStartHour() {
		return __dayStartHour;
	}

	public WeeklyDueDate setDayStartHour(int dayStartHour) {
		this.__dayStartHour = dayStartHour;
		return this;
	}

	public DayOfWeek getWeekStartDay() {
		return __weekStartDay;
	}

	public WeeklyDueDate setWeekStartDay(DayOfWeek weekStartDay) {
		this.__weekStartDay = weekStartDay;
		return this;
	}

	private int __convertDayOfWeekToIndex(DayOfWeek day) {
		return day.getValue() % WEEK_LENGTH;
	}

	private DayOfWeek __convertIndexToDayOfWeek(int index) {
		return DayOfWeek.of(index == 0 ? WEEK_LENGTH : index);
	}

	private DayOfWeek __findPrevDayOfWeek(
			DayOfWeek checkinDay,
			boolean isActiveWeek) {
		var previousDay = DayOfWeek.SATURDAY;
		var checkinDayValue = __convertDayOfWeekToIndex(checkinDay);
		for (int i = 0; i < WEEK_LENGTH; i++) {
			if (isActiveWeek) {
				var dayValue = ((WEEK_LENGTH + checkinDayValue - i - 1) % WEEK_LENGTH);
				var day = __convertIndexToDayOfWeek(dayValue);
				
				if (__activeDaysMap.contains(day)) {
					return day;
				}
			}
			else {
				var day = __convertIndexToDayOfWeek(WEEK_LENGTH - i - 1);
				if (__activeDaysMap.contains(day)) {
					return day;
				}
			}
		}
		return previousDay;
	}

	private DayOfWeek __findNextDayOfWeek(DayOfWeek checkinDay) {
		for (int i = 0; i < WEEK_LENGTH; i++) {
			var day = DayOfWeek.of(
				(WEEK_LENGTH + checkinDay.getValue() + i) % WEEK_LENGTH);
			if (__activeDaysMap.contains(day)) {
				return day;
			}
		}
		throw new IllegalStateException("At least one day should be active");
	}

	private int __offsetForSameWeek(
			DayOfWeek checkinDay,
			DayOfWeek prevDay,
			boolean isActiveWeek) {
		/*
		if checkin day is in active week but before all active days
		push it back a week so that it get's the last active day of
		the previous active weeks
	 */
		return (prevDay.getValue() % WEEK_LENGTH) 
			> (checkinDay.getValue() % WEEK_LENGTH)
				|| (checkinDay == prevDay && isActiveWeek)
			? WEEK_LENGTH 
			: 0;
	}

	private long __distanceFromActiveWeek(long weekNum, long weekScaler) {
		return weekNum % (weekScaler * WEEK_LENGTH);
	}

	public LocalDateTime calculatePreviousDueDate(LocalDateTime checkinDate) {
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
		
		var previousCheckinDayOfTheWeek = previousCheckinDatePrepared
			.getDayOfWeek()
			.plus(__weekStartDay.getValue() % WEEK_LENGTH);

		if (!__activeDaysMap.contains(previousCheckinDayOfTheWeek)) {
			throw new IllegalStateException(
				"previous checkin day is not an active day");
		}

		var firstDayOfFirstWeek = previousCheckinDatePrepared
			.minusDays(
				(previousCheckinDayOfTheWeek.getValue() % WEEK_LENGTH));

		var daySpan = ChronoUnit.DAYS.between(
			firstDayOfFirstWeek,
			checkinDatePrepared);

		var checkinDayOfTheWeek = checkinDatePrepared.getDayOfWeek();

		var firstSunToPrevSunSpan = 
			daySpan - (checkinDayOfTheWeek.getValue() % WEEK_LENGTH);

		var isActiveWeek = __distanceFromActiveWeek(
			firstSunToPrevSunSpan,
			__intervalSize) == 0;

		var prevDueDayOfWeek = __findPrevDayOfWeek(
			checkinDayOfTheWeek,
			isActiveWeek);

		firstSunToPrevSunSpan -= __offsetForSameWeek(
			checkinDayOfTheWeek,
			prevDueDayOfWeek, 
			isActiveWeek);

		var sunOfPrevActiveWeek = firstSunToPrevSunSpan - __distanceFromActiveWeek(
			firstSunToPrevSunSpan, 
			__intervalSize);
		
		return firstDayOfFirstWeek.plusDays(
			sunOfPrevActiveWeek + (prevDueDayOfWeek.getValue() % WEEK_LENGTH));
	}

	private DueDatePair __calculateBothDueDates(LocalDateTime checkinDate) {
		var previousDueDate = calculatePreviousDueDate(
			checkinDate);
		var prevDay = previousDueDate.getDayOfWeek()
			.plus(__weekStartDay.getValue());
		var firstDayOfPreviousWeek = previousDueDate.minusDays(prevDay.getValue());

		var daySpan = ChronoUnit.DAYS.between(
			checkinDate,
			firstDayOfPreviousWeek);

		var checkinDay = checkinDate.getDayOfWeek().plus(__weekStartDay.getValue());

		var prevSunToThisSunSpan = daySpan - checkinDay.getValue();

		var weekCount = 
			(__distanceFromActiveWeek(daySpan, __intervalSize) / WEEK_LENGTH);

		var nextActiveWeek = prevSunToThisSunSpan 
			+ (((__intervalSize - weekCount) % __intervalSize) * WEEK_LENGTH);
		
		var weekStartDay = weekCount == 0
			? checkinDay
			: DayOfWeek.SUNDAY;

		var nextDay = __findNextDayOfWeek(weekStartDay);

		var sameWeekOffset = nextDay.getValue() < checkinDay.getValue() 
				&& weekCount == 0
			? __intervalSize * WEEK_LENGTH
			: 0;
		
		var nextDueDate = firstDayOfPreviousWeek
			.plusDays(nextActiveWeek + nextDay.getValue() + sameWeekOffset);

		return new DueDatePair(previousDueDate, nextDueDate);
		
	}

	public LocalDateTime calculateNextDueDate(LocalDateTime checkinDate
	) {
		return __calculateBothDueDates(checkinDate).next();
	}

	public boolean isDateADueDate(LocalDateTime checkinDate) {
		var nextDueDate = __calculateBothDueDates(checkinDate).next();

		return nextDueDate.toLocalDate().isEqual(checkinDate.toLocalDate());
	}	
}

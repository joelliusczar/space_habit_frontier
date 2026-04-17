package space_habit_frontier.engine.dtos.todos;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class WeeklyDueDate {
	private Set<DayOfWeek> __activeDaysMap;
	private ZonedDateTime __previousCheckinDate;
	private long __intervalSize;
	private int __dayStartHour;
	private DayOfWeek __weekStartDay;

	public WeeklyDueDate(
		Set<DayOfWeek> activeDaysMap,
		ZonedDateTime previousCheckinDate) {
		this.__activeDaysMap = activeDaysMap;
		this.__previousCheckinDate = previousCheckinDate;
		this.__intervalSize = 1;
		this.__dayStartHour = 0;
		this.__weekStartDay = DayOfWeek.SUNDAY;
	}


	public Set<DayOfWeek> getActiveDaysMap() {
		return __activeDaysMap;
	}

	public WeeklyDueDate setActiveDaysMap(Set<DayOfWeek> activeDays) {
		this.__activeDaysMap = activeDays;
		return this;
	}

	public long getIntervalSize() {
		return __intervalSize;
	}

	public WeeklyDueDate setIntervalSize(long intervalSize) {
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

	private ZonedDateTime prepareDateForCalculations(
			ZonedDateTime date,
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

	private DayOfWeek __findPrevDayOfWeek(
			DayOfWeek checkinDay,
			boolean isActiveWeek) {
		var previousDay = DayOfWeek.SATURDAY;
		for (int i = 0; i < 7; i++) {
			if (isActiveWeek) {
				var day = DayOfWeek.of((7 + checkinDay.getValue() - i - 1) % 7);
				
				if (__activeDaysMap.contains(day)) {
					return day;
				}
			}
			else {
				var day = DayOfWeek.of(7 - i + 1);
				if (__activeDaysMap.contains(day)) {
					return day;
				}
			}
		}
		return previousDay;
	}

	private DayOfWeek __findNextDayOfWeek(DayOfWeek checkinDay) {
		for (int i = 0; i < 7; i++) {
			var day = DayOfWeek.of((7 + checkinDay.getValue() + i) % 7);
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
		return prevDay.getValue() > checkinDay.getValue() 
				|| (checkinDay == prevDay && isActiveWeek)
			? 7 
			: 0;
	}

	private long __distanceFromActiveWeek(long weekNum, long weekScaler) {
		return weekNum % (weekScaler * 7);
	}

	public ZonedDateTime calculatePreviousDueDate(ZonedDateTime checkinDate) {
		var previousDueDatePrepared = prepareDateForCalculations(
			__previousCheckinDate,
			__dayStartHour);
		var checkinDatePrepared = prepareDateForCalculations(
			checkinDate,
			__dayStartHour);
		
		var previousDueDayOfTheWeek = previousDueDatePrepared
			.getDayOfWeek()
			.plus(__weekStartDay.getValue());

		if (!__activeDaysMap.contains(previousDueDayOfTheWeek)) {
			return null;
		}

		var firstDayOfFirstWeek = previousDueDatePrepared
			.minusDays((previousDueDayOfTheWeek.getValue() % 7));

		var daySpan = ChronoUnit.DAYS.between(
			checkinDatePrepared,
			firstDayOfFirstWeek);

		var checkinDayOfTheWeek = checkinDatePrepared.getDayOfWeek();

		var firstSunToPrevSunSpan = daySpan - (checkinDayOfTheWeek.getValue() % 7);

		
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
				sunOfPrevActiveWeek + (prevDueDayOfWeek.getValue() % 7));
	}

	private DueDatePair __calculateBothDueDates(ZonedDateTime checkinDate) {
		var previousDueDate = calculatePreviousDueDate(
			checkinDate);
		var prevDay = previousDueDate.getDayOfWeek().plus(__weekStartDay.getValue());
		var firstDayOfPreviousWeek = previousDueDate.minusDays(prevDay.getValue());

		var daySpan = ChronoUnit.DAYS.between(
			checkinDate,
			firstDayOfPreviousWeek);

		var checkinDay = checkinDate.getDayOfWeek().plus(__weekStartDay.getValue());

		var prevSunToThisSunSpan = daySpan - checkinDay.getValue();

		var weekCount = (__distanceFromActiveWeek(daySpan, __intervalSize) / 7);

		var nextActiveWeek = prevSunToThisSunSpan 
			+ (((__intervalSize - weekCount) % __intervalSize) * 7);
		
		var weekStartDay = weekCount == 0
			? checkinDay
			: DayOfWeek.SUNDAY;

		var nextDay = __findNextDayOfWeek(weekStartDay);

		var sameWeekOffset = nextDay.getValue() < checkinDay.getValue() 
				&& weekCount == 0
			? __intervalSize * 7
			: 0;
		
		var nextDueDate = firstDayOfPreviousWeek
			.plusDays(nextActiveWeek + nextDay.getValue() + sameWeekOffset);

		return new DueDatePair(previousDueDate, nextDueDate);
		
	}

	public ZonedDateTime calculateNextDueDate(ZonedDateTime checkinDate
	) {
		return __calculateBothDueDates(checkinDate).next();
	}

	public boolean isDateADueDate(ZonedDateTime checkinDate) {
		var nextDueDate = __calculateBothDueDates(checkinDate).next();

		return nextDueDate.toLocalDate().isEqual(checkinDate.toLocalDate());
	}	
}

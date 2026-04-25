package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public abstract class DueDate {
	private Set<Integer> __activeDaysMap;
	private LocalDateTime __previousCheckinDate;
	private long __intervalSize;
	private int __dayStartHour;
	private short __periodOffset;

	public DueDate(
		Set<Integer> activeDaysMap,
		LocalDateTime previousCheckinDate) {
		this.__activeDaysMap = activeDaysMap;
		this.__previousCheckinDate = previousCheckinDate;
		this.__intervalSize = 1;
		this.__dayStartHour = 0;
		this.__periodOffset = 0;
	}

	public LocalDateTime getPreviousCheckinDate() {
		return __previousCheckinDate;
	}

	public DueDate setPreviousCheckinDate(
			LocalDateTime previousCheckinDate) {
		this.__previousCheckinDate = previousCheckinDate;
		return this;
	}

	public Set<Integer> getActiveDaysMap() {
		return __activeDaysMap;
	}

	public DueDate setActiveDaysMap(Set<Integer> activeDays) {
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

	public DueDate setIntervalSize(long intervalSize) {
		if (intervalSize < 1) {
			throw new IllegalArgumentException("intervalSize must be at least 1");
		}
		this.__intervalSize = intervalSize;
		return this;
	}

	public int getDayStartHour() {
		return __dayStartHour;
	}

	public DueDate setDayStartHour(int dayStartHour) {
		this.__dayStartHour = dayStartHour;
		return this;
	}

	public short periodOffset() {
		return __periodOffset;
	}

	public DueDate setPeriodOffset(short periodOffset) {
		this.__periodOffset = periodOffset;
		return this;
	}

	protected abstract short periodLength();

	protected abstract short dayOfPeriod(LocalDateTime date);

	private short __findPrevDayOfSpan(
			short checkinDay,
			boolean isActiveWeek) {
		var previousDay = 0;

		for (short i = 0; i < periodLength(); i++) {
			if (isActiveWeek) {
				var dayValue = ((periodLength() + checkinDay - i - 1) % periodLength());
				
				if (__activeDaysMap.contains(dayValue)) {
					return (short)dayValue;
				}
			}
			else {
				var day = periodLength() - i - 1;
				if (__activeDaysMap.contains(day)) {
					return (short)day;
				}
			}
		}
		return (short)previousDay;
	}

	private short __findNextDayOfPeriod(short checkinDay) {
		for (short i = 0; i < periodLength(); i++) {
			var day = (periodLength() + checkinDay + i) % periodLength();
			if (__activeDaysMap.contains(day)) {
				return (short)day;
			}
		}
		throw new IllegalStateException("At least one day should be active");
	}

	private int __offsetForSamePeriod(
			short checkinDay,
			short prevDay,
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

	private long __distanceFromActivePeriod(long periodNum, long periodScaler) {
		return periodNum % (periodScaler * periodLength());
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
		
		var previousCheckinDayOfPeriod = dayOfPeriod(previousCheckinDatePrepared)
			+ (__periodOffset % periodLength());

		if (!__activeDaysMap.contains(previousCheckinDayOfPeriod)) {
			throw new IllegalStateException(
				"previous checkin day is not an active day");
		}

		var firstDayOfFirstWeek = previousCheckinDatePrepared
			.minusDays(
				(previousCheckinDayOfPeriod % periodLength()));

		var daySpan = ChronoUnit.DAYS.between(
			firstDayOfFirstWeek,
			checkinDatePrepared);

		var checkinDayOfTheWeek = dayOfPeriod(checkinDatePrepared);

		var firstStartToPrevStartSpan = 
			daySpan - (checkinDayOfTheWeek % periodLength());

		var isActiveWeek = __distanceFromActivePeriod(
			firstStartToPrevStartSpan,
			__intervalSize) == 0;

		var prevDueDayOfWeek = __findPrevDayOfSpan(
			checkinDayOfTheWeek,
			isActiveWeek);

		firstStartToPrevStartSpan -= __offsetForSamePeriod(
			checkinDayOfTheWeek,
			prevDueDayOfWeek, 
			isActiveWeek);

		var distanceFromActivePeriod = __distanceFromActivePeriod(
				firstStartToPrevStartSpan, 
				__intervalSize);

		var startOfPrevActiveWeek = 
			firstStartToPrevStartSpan - distanceFromActivePeriod;
		
		return firstDayOfFirstWeek.plusDays(
			startOfPrevActiveWeek + (prevDueDayOfWeek % periodLength()));
	}

	private DueDatePair __calculateBothDueDates(LocalDateTime checkinDate) {
		var previousDueDate = calculatePreviousDueDate(
			checkinDate);
		var prevDay = dayOfPeriod(previousDueDate) + __periodOffset;
		var firstDayOfPreviousPeriod = previousDueDate.minusDays(prevDay);

		var daySpan = ChronoUnit.DAYS.between(
			checkinDate,
			firstDayOfPreviousPeriod);

		var checkinDay = dayOfPeriod(checkinDate) + __periodOffset;

		var prevSunToThisSunSpan = daySpan - checkinDay;

		var weekCount = 
			(__distanceFromActivePeriod(daySpan, __intervalSize) / periodLength());

		var nextActiveWeek = prevSunToThisSunSpan 
			+ (((__intervalSize - weekCount) % __intervalSize) * periodLength());
		
		var periodStartDay = weekCount == 0
			? checkinDay
			: 0;

		var nextDay = __findNextDayOfPeriod((short)periodStartDay);

		var samePeriodOffset = nextDay < checkinDay 
				&& weekCount == 0
			? __intervalSize * periodLength()
			: 0;
		
		var nextDueDate = firstDayOfPreviousPeriod
			.plusDays(nextActiveWeek + nextDay + samePeriodOffset);

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

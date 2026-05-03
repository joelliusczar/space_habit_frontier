package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Set;

public abstract class DueDateCalculator {
	private Set<Integer> __activeDays;
	private LocalDateTime __previousCheckinDate;
	private LocalDate __preparedPreviousCheckinDate;
	private int __intervalSize;
	private LocalTime __dayStartHour;
	private int __periodOffset;

	public DueDateCalculator(
		Set<Integer> activeDays,
		LocalDateTime previousCheckinDate) {
		this.__activeDays = activeDays;
		this.__previousCheckinDate = previousCheckinDate;
		this.__intervalSize = 1;
		this.__dayStartHour = LocalTime.MIDNIGHT;
		this.__periodOffset = 0;
	}

	public LocalDateTime previousCheckinDate() {
		return __previousCheckinDate;
	}

	public DueDateCalculator setPreviousCheckinDate(
			LocalDateTime previousCheckinDate) {
		this.__previousCheckinDate = previousCheckinDate;
		this.__preparedPreviousCheckinDate = prepareDate(__previousCheckinDate);
		return this;
	}

	public LocalDate preparedPreviousCheckinDate() {
		if (__preparedPreviousCheckinDate == null) {
			__preparedPreviousCheckinDate = prepareDate(__previousCheckinDate);
		}
		
		return __preparedPreviousCheckinDate;
	}

	public Set<Integer> activeDays() {
		return __activeDays;
	}

	public DueDateCalculator setActiveDays(Set<Integer> activeDays) {
		if (activeDays.isEmpty()) {
			throw new IllegalArgumentException(
				"Active days must have at least one element.");
		}
		this.__activeDays = activeDays;
		return this;
	}

	public int intervalSize() {
		return __intervalSize;
	}

	public DueDateCalculator setIntervalSize(int intervalSize) {
		if (intervalSize < 1) {
			throw new IllegalArgumentException("intervalSize must be at least 1");
		}
		this.__intervalSize = intervalSize;
		return this;
	}

	public LocalTime dayStartHour() {
		return __dayStartHour;
	}

	public DueDateCalculator setDayStartHour(LocalTime dayStartHour) {
		this.__dayStartHour = dayStartHour;
		return this;
	}

	public int periodOffset() {
		return __periodOffset;
	}

	public DueDateCalculator setPeriodOffset(short periodOffset) {
		this.__periodOffset = periodOffset;
		return this;
	}

	protected abstract int periodLength();

	protected abstract int dayOfPeriod(LocalDate date);

	protected abstract int dayOfPeriod(LocalDate date, int offset);

	protected abstract int dayOfPeriod(LocalDateTime date);

	protected abstract int dayOfPeriod(LocalDateTime date, int offset);

	public abstract long periodsBetween(
		Temporal inclusive,
		Temporal exclusive);

	public LocalDate prepareDate(LocalDateTime date) {
		if(date.toLocalTime().isBefore(__dayStartHour) 
				|| date.toLocalTime().equals(__dayStartHour)) {
			return date.toLocalDate();
		}
		return date.minusDays(1).toLocalDate();
	}

	private int __findPrevDayOfPeriod(
			int checkinDay,
			boolean isActiveWeek) {
		var previousDay = 0;

		for (int i = 0; i < periodLength(); i++) {
			if (isActiveWeek) {
				var dayValue = ((periodLength() + checkinDay - i - 1) % periodLength());
				
				if (__activeDays.contains(dayValue)) {
					return dayValue;
				}
			}
			else {
				var day = periodLength() - i - 1;
				if (__activeDays.contains(day)) {
					return day;
				}
			}
		}
		return previousDay;
	}

	private int __findNextDayOfPeriod(int checkinDay) {
		for (int i = 0; i < periodLength(); i++) {
			var day = (periodLength() + checkinDay + i) % periodLength();
			if (__activeDays.contains(day)) {
				return day;
			}
		}
		throw new IllegalStateException("At least one day should be active");
	}

	private int __offsetForSamePeriod(
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

	private long __distanceFromActivePeriod(long periodNum, int periodScaler) {
		return periodNum % (periodScaler * periodLength());
	}

	public LocalDate calculatePreviousDueDate(LocalDateTime checkinDate) {
		var checkinDatePrepared = prepareDate(checkinDate);
		return calculatePreviousDueDate(checkinDatePrepared);
	}

	public LocalDate calculatePreviousDueDate(LocalDate checkinDatePrepared) {
		var previousCheckinDatePrepared = prepareDate(previousCheckinDate());

		if (previousCheckinDatePrepared.isAfter(checkinDatePrepared) 
				|| previousCheckinDatePrepared.isEqual(checkinDatePrepared)) {
			throw new IllegalArgumentException(
				"Checkin date should be after previous checkin date");
		}
		
		var previousCheckinDayOfPeriod = dayOfPeriod(previousCheckinDatePrepared)
			+ (periodOffset() % periodLength());

		if (!activeDays().contains(previousCheckinDayOfPeriod)) {
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
			intervalSize()) == 0;

		var prevDueDayOfWeek = __findPrevDayOfPeriod(
			checkinDayOfTheWeek,
			isActiveWeek);

		firstStartToPrevStartSpan -= __offsetForSamePeriod(
			checkinDayOfTheWeek,
			prevDueDayOfWeek, 
			isActiveWeek);

		var distanceFromActivePeriod = __distanceFromActivePeriod(
				firstStartToPrevStartSpan, 
				intervalSize());

		var startOfPrevActiveWeek = 
			firstStartToPrevStartSpan - distanceFromActivePeriod;
		
		return firstDayOfFirstWeek.plusDays(
				startOfPrevActiveWeek + (prevDueDayOfWeek % periodLength()));
	}

	private DueDatePair __calculateBothDueDates(LocalDateTime checkinDate) {
		var previousDueDate = calculatePreviousDueDate(
			checkinDate);
		var prevDay = dayOfPeriod(previousDueDate) + periodOffset();
		var firstDayOfPreviousPeriod = previousDueDate.minusDays(prevDay);

		var daySpan = ChronoUnit.DAYS.between(
			firstDayOfPreviousPeriod,
			checkinDate);

		var checkinDay = dayOfPeriod(checkinDate) + periodOffset();

		var prevSunToThisSunSpan = daySpan - checkinDay;

		var weekCount = 
			(__distanceFromActivePeriod(daySpan, intervalSize()) / periodLength());

		var nextActiveWeek = prevSunToThisSunSpan 
			+ (((intervalSize() - weekCount) % intervalSize()) * periodLength());
		
		var periodStartDay = weekCount == 0
			? checkinDay
			: 0;

		var nextDay = __findNextDayOfPeriod(periodStartDay);

		var samePeriodOffset = nextDay < checkinDay 
				&& weekCount == 0
			? intervalSize() * periodLength()
			: 0;
		
		var nextDueDate = firstDayOfPreviousPeriod
			.plusDays(nextActiveWeek + nextDay + samePeriodOffset);

		return new DueDatePair(previousDueDate, nextDueDate);
		
	}

	public LocalDate calculateNextDueDate(LocalDateTime checkinDate
	) {
		return __calculateBothDueDates(checkinDate).next();
	}

	public boolean isDateADueDate(LocalDateTime checkinDate) {
		var nextDueDate = __calculateBothDueDates(checkinDate).next();

		return nextDueDate.isEqual(checkinDate.toLocalDate());
	}

	private LocalDate __periodStart(LocalDate date) {
		var day = dayOfPeriod(date);
		return date.minusDays(day);
	}

	private LocalDate __nextPeriodStart(LocalDate date) {
		var day = dayOfPeriod(date);
		return date.plusDays(periodLength() - day);
	}

	private PeriodBounds __constructPeriodBounds(LocalDate point) {
		return new PeriodBounds(__periodStart(point), __nextPeriodStart(point));
	}

	private long __missedDaysSamePeriod(LocalDate preparedCheckinDate) {
		var checkinDay = dayOfPeriod(preparedCheckinDate);
		var previousCheckinDay = dayOfPeriod(preparedPreviousCheckinDate());
		
		return activeDays().stream()
		.filter(i -> i > previousCheckinDay
			&& i < checkinDay)
		.count();
	}

	private MissedDaysDto __constructMissedDaysDto(LocalDate checkinDate) {
		var previousCheckinDate = preparedPreviousCheckinDate();
		var checkinDay = dayOfPeriod(checkinDate);
		var previousCheckinDay = dayOfPeriod(previousCheckinDate);
		var firstPartialWeekCount = activeDays()
			.stream()
			.filter(i -> i > previousCheckinDay)
			.count();
		var lastPartialWeekCount = activeDays()
			.stream()
			.filter(i -> i <= checkinDay)
			.count();

		var fullWeekCount = Math.abs(periodsBetween(
			__nextPeriodStart(previousCheckinDate.plusDays(periodOffset())),
			__periodStart(checkinDate.plusDays(periodOffset()))));

		return new MissedDaysDto(
			firstPartialWeekCount, 
			fullWeekCount, 
			lastPartialWeekCount);
	}

	private long __missedDays(LocalDate previousDueDate) {
		var missedDaysDto = __constructMissedDaysDto(previousDueDate);
		
		if (missedDaysDto.fullPeriodCount() < 1) {
			throw new RuntimeException(
				"fullPeriodCount should only be less than 1 if same week");
		}

		var adjustedWeekCount = missedDaysDto.fullPeriodCount() / intervalSize();
		return missedDaysDto.firstPartialPeriodCount() 
			+ (adjustedWeekCount * activeDays().size())
			+ missedDaysDto.lastPartialPeriodCount();
	}

	public long missedDays (LocalDateTime checkinDate) {

		var checkinDatePrepared = prepareDate(checkinDate);
		if (!(checkinDatePrepared.isAfter(preparedPreviousCheckinDate())
				|| checkinDatePrepared.isEqual(preparedPreviousCheckinDate()))) {
			throw new IllegalArgumentException(
				"checkinDate must be more recent than the previous checkin date");
		}

		var periodBounds = __constructPeriodBounds(
			checkinDatePrepared.plusDays(periodOffset()));
		
		if (periodBounds.isWithinPeriod(preparedPreviousCheckinDate())) {
			return __missedDaysSamePeriod(checkinDatePrepared);
		}

		var previousDueDate = calculatePreviousDueDate(checkinDatePrepared);
		if (previousDueDate.isEqual(preparedPreviousCheckinDate())) {
			return 0;
		}

		var previousPeriodBounds = __constructPeriodBounds(
			previousDueDate.plusDays(periodOffset()));
		
		if (previousPeriodBounds.isWithinPeriod(preparedPreviousCheckinDate())) {
			//I think the +1 is an offset to include previousDueDate itself
			return __missedDaysSamePeriod(previousDueDate) + 1;
		}

		return __missedDays(previousDueDate);
	}
}

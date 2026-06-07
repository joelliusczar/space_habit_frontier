package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import space_habit_frontier.engine.dtos.todos.active_days.ActiveDaysCollection;

public class DueDateCalculator implements DateAligner {
	private ActiveDaysCollection __activeDays;
	private LocalDateTime __previousCheckinDate;
	private LocalDate __previousCheckinDateAligned;
	private int __intervalSize;
	private LocalTime __dayStartHour;

	public DueDateCalculator(
		ActiveDaysCollection activeDays,
		LocalDateTime previousCheckinDate) {
		this.__activeDays = activeDays;
		this.__previousCheckinDate = previousCheckinDate;
		this.__intervalSize = 1;
		this.__dayStartHour = LocalTime.MIDNIGHT;
	}

	public LocalDateTime previousCheckinDate() {
		return __previousCheckinDate;
	}

	public DueDateCalculator setPreviousCheckinDate(
			LocalDateTime previousCheckinDate) {
		this.__previousCheckinDate = previousCheckinDate;
		this.__previousCheckinDateAligned = alignDate(__previousCheckinDate);
		return this;
	}

	public LocalDate previousCheckinDateAligned() {
		if (__previousCheckinDateAligned == null) {
			__previousCheckinDateAligned = alignDate(__previousCheckinDate);
		}
		return __previousCheckinDateAligned;
	}

	public ActiveDaysCollection activeDays() {
		return __activeDays;
	}

	public DueDateCalculator setActiveDays(ActiveDaysCollection activeDays) {
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

	@Override
	public LocalTime dayStartHour() {
		return __dayStartHour;
	}

	@Override
	public DueDateCalculator setDayStartHour(LocalTime dayStartHour) {
		this.__dayStartHour = dayStartHour;
		return this;
	}

	public LocalDate calculatePreviousDueDate(LocalDateTime checkinDate) {
		return calculatePreviousDueDate(alignDate(checkinDate));
	}

	public LocalDate calculatePreviousDueDate(LocalDate checkinDateAligned) {
		//previousCheckinDateAligned
		var previousCheckinDateAligned = alignDate(previousCheckinDate());
		var previousCheckinDateActive = activeDays()
			.nextActiveDay(previousCheckinDateAligned)
			.orElseThrow(() -> 
				new IllegalStateException("At least one day should be active"));

		if (previousCheckinDateActive.isAfter(checkinDateAligned) 
				|| previousCheckinDateActive.isEqual(checkinDateAligned)) {
			return previousCheckinDateActive;
		}

		var previousCheckinDayOfPeriod = activeDays()
			.dayOfPeriod(previousCheckinDateActive);

		var firstDayOfFirstWeek = previousCheckinDateActive
			.minusDays(
				(previousCheckinDayOfPeriod % activeDays().periodLength()));

		var daySpan = ChronoUnit.DAYS.between(
			firstDayOfFirstWeek,
			checkinDateAligned);

		var checkinDayOfTheWeek = activeDays().dayOfPeriod(checkinDateAligned);

		var firstStartToPrevStartSpan = 
			daySpan - (checkinDayOfTheWeek % activeDays().periodLength());

		var isActiveWeek = activeDays().distanceFromActivePeriod(
			firstStartToPrevStartSpan,
			intervalSize()) == 0;

		var prevDueDayOfWeek = activeDays().findPrevDayOfPeriod(
			checkinDayOfTheWeek,
			isActiveWeek);

		firstStartToPrevStartSpan -= activeDays().offsetForSamePeriod(
			checkinDayOfTheWeek,
			prevDueDayOfWeek, 
			isActiveWeek);

		var distanceFromActivePeriod = activeDays().distanceFromActivePeriod(
			firstStartToPrevStartSpan, 
			intervalSize());

		var startOfPrevActiveWeek = 
			firstStartToPrevStartSpan - distanceFromActivePeriod;
		
		return firstDayOfFirstWeek.plusDays(
				startOfPrevActiveWeek + (prevDueDayOfWeek % activeDays().periodLength()));
	}

	private DueDatePair __calculateBothDueDates(LocalDateTime checkinDate) {
		var checkinDateAligned = alignDate(checkinDate);
		var previousDueDate = calculatePreviousDueDate(checkinDateAligned);

		if (previousDueDate.isAfter(checkinDateAligned) 
				|| previousDueDate.isEqual(checkinDateAligned)) {
			return new DueDatePair(Optional.empty(), previousDueDate);
		}

		var prevDay = activeDays().dayOfPeriod(previousDueDate);
		var firstDayOfPreviousPeriod = previousDueDate.minusDays(prevDay);

		var daySpan = ChronoUnit.DAYS.between(
			firstDayOfPreviousPeriod,
			checkinDate);

		var checkinDay = activeDays().dayOfPeriod(checkinDate);

		var prevSunToThisSunSpan = daySpan - checkinDay;

		var weekCount = 
			(activeDays().distanceFromActivePeriod(daySpan, intervalSize()) 
				/ activeDays().periodLength());

		var nextActiveWeek = prevSunToThisSunSpan 
			+ (((intervalSize() - weekCount) % intervalSize()) 
				* activeDays().periodLength());
		
		var periodStartDay = weekCount == 0
			? checkinDay
			: 0;

		var nextDay = activeDays().findNextDayOfPeriod(periodStartDay);

		var samePeriodOffset = nextDay < checkinDay 
				&& weekCount == 0
			? intervalSize() * activeDays().periodLength()
			: 0;
		
		var nextDueDate = firstDayOfPreviousPeriod
			.plusDays(nextActiveWeek + nextDay + samePeriodOffset);

		return new DueDatePair(Optional.of(previousDueDate), nextDueDate);
	}

	public LocalDate calculateNextDueDate(LocalDateTime checkinDate) {
		return __calculateBothDueDates(checkinDate).next();
	}

	public boolean isDateADueDate(LocalDateTime checkinDate) {
		var nextDueDate = __calculateBothDueDates(checkinDate).next();

		return nextDueDate.isEqual(checkinDate.toLocalDate());
	}

	private LocalDate __periodStart(LocalDate date) {
		var day = activeDays().dayOfPeriod(date);
		return date.minusDays(day);
	}

	private LocalDate __nextPeriodStart(LocalDate date) {
		var day = activeDays().dayOfPeriod(date);
		return date.plusDays(activeDays().periodLength() - day);
	}

	private PeriodBounds __constructPeriodBounds(LocalDate point) {
		return new PeriodBounds(__periodStart(point), __nextPeriodStart(point));
	}

	private long __missedDaysSamePeriod(LocalDate preparedCheckinDate) {
		var checkinDay = activeDays().dayOfPeriod(preparedCheckinDate);
		var previousCheckinDay = activeDays().dayOfPeriod(previousCheckinDateAligned());
		
		return activeDays().stream()
		.filter(i -> i > previousCheckinDay
			&& i < checkinDay)
		.count();
	}

	private MissedDaysDto __constructMissedDaysDto(LocalDate checkinDate) {
		var previousCheckinDate = previousCheckinDateAligned();
		var checkinDay = activeDays().dayOfPeriod(checkinDate);
		var previousCheckinDay = activeDays().dayOfPeriod(previousCheckinDate);
		var firstPartialWeekCount = activeDays()
			.stream()
			.filter(i -> i > previousCheckinDay)
			.count();
		var lastPartialWeekCount = activeDays()
			.stream()
			.filter(i -> i <= checkinDay)
			.count();

		var fullWeekCount = Math.abs(activeDays().periodsBetween(
			__nextPeriodStart(previousCheckinDate),
			__periodStart(checkinDate)));

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
		var checkinDatePrepared = alignDate(checkinDate);
		if (!(checkinDatePrepared.isAfter(previousCheckinDateAligned())
				|| checkinDatePrepared.isEqual(previousCheckinDateAligned()))) {
			throw new IllegalArgumentException(
				"checkinDate must be more recent than the previous checkin date");
		}

		var periodBounds = __constructPeriodBounds(
			checkinDatePrepared);
		
		if (periodBounds.isWithinPeriod(previousCheckinDateAligned())) {
			return __missedDaysSamePeriod(checkinDatePrepared);
		}

		var previousDueDate = calculatePreviousDueDate(checkinDatePrepared);
		if (previousDueDate.isEqual(previousCheckinDateAligned())) {
			return 0;
		}

		var previousPeriodBounds = __constructPeriodBounds(
			previousDueDate);
		
		if (previousPeriodBounds.isWithinPeriod(previousCheckinDateAligned())) {
			//I think the +1 is an offset to include previousDueDate itself
			return __missedDaysSamePeriod(previousDueDate) + 1;
		}

		return __missedDays(previousDueDate);
	}

	public static TodoListDto setCalculatedDates(
			TodoListDto todo,
			LocalDateTime checkinDate) {
		var previousCompletion = todo.lastCompletedDatetime()
					.orElse(todo.weekActiveDays().minActiveDate().atTime(OffsetTime.MIN))
					.toLocalDateTime();
		var calculator = new DueDateCalculator(
			todo.weekActiveDays(),
			previousCompletion);
		var dueDate = calculator
			.calculateNextDueDate(checkinDate);
		return todo.setNextDueDate(dueDate);
	}
	
}

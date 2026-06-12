package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.BitSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.NotImplementedException;

import space_habit_frontier.engine.constants.RepeatType;
import space_habit_frontier.engine.dtos.TitledId;
import space_habit_frontier.engine.dtos.todos.active_days.ActiveDaysCollection;
import space_habit_frontier.engine.dtos.todos.active_days.WeeklyActiveDaysCollection;

public class TodoListDto extends TitledId implements DateAligner {

	private Optional<OffsetDateTime> __lastCompletedDatetime;
	private Optional<LocalDate> __nextDueDate;
	private Optional<OffsetDateTime> __effectiveDatetime;
	private Optional<OffsetDateTime> __creationDateTimetime;
	private RepeatType __repeatType;
	private Set<Integer> __weekActiveDaysSet;
	private LocalTime __dayStartHour;
	private int __damage;
	private int __intervalSize;

	public TodoListDto(UUID id, String title) {
		super(id, title);
	}

	public Optional<OffsetDateTime> lastCompletedDatetime (){
		return __lastCompletedDatetime;
	}

	public TodoListDto setLastCompletedDatetime(OffsetDateTime value) {
		__lastCompletedDatetime = Optional.ofNullable(value);
		return this;
	}

	public Optional<OffsetDateTime> effectiveDatetime() {
		return __effectiveDatetime;
	}

	public TodoListDto setEffectiveDatetime(OffsetDateTime value) {
		__effectiveDatetime = Optional.ofNullable(value);
		return this;
	}

	public Optional<OffsetDateTime> creationDatetime() {
		return __creationDateTimetime;
	}

	public TodoListDto setCreationDatetime(OffsetDateTime value) {
		__creationDateTimetime = Optional.ofNullable(value);
		return this;
	}

	public OffsetDateTime lastCompletedDatetimeBestGuess(OffsetDateTime now) {
		return lastCompletedDatetime()
			.orElseGet(() -> {
				if (effectiveDatetime().isPresent()) {
					var effectiveDatetime = effectiveDatetime().get();
					if (effectiveDatetime.isBefore(now)) {
						return effectiveDatetime;
					}
				}
				var creationDatetime = creationDatetime().orElseThrow();
				if (creationDatetime.isBefore(now)) {
					return creationDatetime;
				}
				throw new NoSuchElementException(
					"No reasonable value could be found for lastCompletedDatetime");
			});
	}

	public Optional<LocalDate> nextDueDate() {
		return __nextDueDate;
	}

	public TodoListDto setNextDueDate(LocalDate value) {
		__nextDueDate = Optional.of(value);
		return this;
	}

	public RepeatType repeatType() {
		return __repeatType;
	}

	public TodoListDto setRepeatType(RepeatType value) {
		__repeatType = value;
		return this;
	}

	public Set<Integer> weekActiveDaysSet() {
		return __weekActiveDaysSet;
	}

	public TodoListDto setWeekActiveDaysSet(Set<Integer> value) {
		__weekActiveDaysSet = value;
		return this;
	}

	public WeeklyActiveDaysCollection weekActiveDays() {
		return new WeeklyActiveDaysCollection(weekActiveDaysSet());
	}

	public TodoListDto setWeekActiveDaysSet(BitSet bits) {
		return this;
	}

	public ActiveDaysCollection activeDaysCollection() {
		
		switch (repeatType()) {
			case RepeatType.WEEKLY:
				return weekActiveDays();
			default:
				throw new NotImplementedException(
					String.format("Haven't figured out what to do for %s yet",
						repeatType().getFriendlyName()));
		}
	}

	public int damage() {
		return __damage;
	}

	public TodoListDto setDamage(int damage) {
		__damage = damage;
		return this;
	}

	public int intervalSize() {
		return __intervalSize;
	}

	public TodoListDto setIntervalSize(int intervalSize) {
		__intervalSize = intervalSize;
		return this;
	}

	@Override
	public LocalTime dayStartHour() {
		return __dayStartHour;
	}

	@Override
	public TodoListDto setDayStartHour(LocalTime dayStartHour) {
		this.__dayStartHour = dayStartHour;
		return this;
	}

	public LocalDate alignLastCompletedDate() {
		if (__lastCompletedDatetime.isEmpty()) {
			return LocalDate.MIN;
		}
		return alignDate(__lastCompletedDatetime.get().toLocalDateTime());
	}
	
}

package space_habit_frontier.engine.dtos.todos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.BitSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import space_habit_frontier.engine.constants.RepeatType;
import space_habit_frontier.engine.dtos.TitledId;
import space_habit_frontier.engine.dtos.todos.active_days.WeeklyActiveDaysCollection;

public class TodoListDto extends TitledId implements DateAligner {

	private Optional<OffsetDateTime> __lastCompletedDatetime;
	private Optional<LocalDate> __nextDueDate;
	private RepeatType __cycleRateType;
	private Set<Integer> __weekActiveDaysSet;
	private LocalTime __dayStartHour;
	

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

	public Optional<LocalDate> nextDueDate() {
		return __nextDueDate;
	}

	public TodoListDto setNextDueDate(LocalDate value) {
		__nextDueDate = Optional.of(value);
		return this;
	}

	public RepeatType cycleRateType() {
		return __cycleRateType;
	}

	public TodoListDto setCycleRateType(RepeatType value) {
		__cycleRateType = value;
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

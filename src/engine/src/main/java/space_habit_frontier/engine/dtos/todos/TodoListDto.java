package space_habit_frontier.engine.dtos.todos;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import space_habit_frontier.engine.dtos.TitledId;

public class TodoListDto extends TitledId {

	private Optional<OffsetDateTime> __lastCompletedDatetime;

	public TodoListDto(UUID id, String title) {
		super(id, title);
	}

	public Optional<OffsetDateTime> lastCompletedDatetime (){
		return __lastCompletedDatetime;
	}

	public TodoListDto setLastCompletedDatetime(OffsetDateTime value) {
		__lastCompletedDatetime = Optional.of(value);
		return this;
	}
	
}

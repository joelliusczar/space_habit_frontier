package space_habit_frontier.engine.dtos.todos;

import java.util.UUID;

import space_habit_frontier.engine.dtos.TitledId;

public class TodoListDto extends TitledId {

	public TodoListDto(UUID id, String title) {
		super(id, title);
	}
	
}

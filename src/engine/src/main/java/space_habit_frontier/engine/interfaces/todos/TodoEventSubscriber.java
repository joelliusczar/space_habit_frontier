package space_habit_frontier.engine.interfaces.todos;

import java.util.UUID;

import space_habit_frontier.engine.dtos.events.RollDto;

public interface TodoEventSubscriber {
	void onTodoCompleted(RollDto rollDto);

	void onTodoReverted(UUID todoId);
}

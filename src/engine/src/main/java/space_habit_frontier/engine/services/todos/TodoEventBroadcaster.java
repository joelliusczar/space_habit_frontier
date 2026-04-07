package space_habit_frontier.engine.services.todos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import space_habit_frontier.engine.dtos.events.RollDto;
import space_habit_frontier.engine.interfaces.todos.TodoEventSubscriber;

public class TodoEventBroadcaster {
	private final List<TodoEventSubscriber> __subscribers = new ArrayList<>();

	public TodoEventBroadcaster(TodoEventSubscriber... subscribers) {
		for (var subscriber : subscribers) {
			__subscribers.add(subscriber);
		}
	}

	public void subscribe(TodoEventSubscriber subscriber) {
		__subscribers.add(subscriber);
	}

	public void broadcastTodoCompletedEvent(RollDto roll) {
		for (var subscriber : __subscribers) {
			subscriber.onTodoCompleted(roll);
		}
	}

	public void broadcastTodoRevertedEvent(UUID todoId) {
		for (var subscriber : __subscribers) {
			subscriber.onTodoReverted(todoId);
		}
	}


}

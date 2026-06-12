package space_habit_frontier.engine.services.user_moves;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import space_habit_frontier.engine.dtos.user_moves.UserMoveDto;
import space_habit_frontier.engine.interfaces.user_moves.UserMoveEventSubscriber;

public class UserMovesEventBroadcaster<T> {
	private final List<UserMoveEventSubscriber<T>> __subscribers 
		= new ArrayList<>();

	public UserMovesEventBroadcaster(List<UserMoveEventSubscriber<T>> subscribers) {
		for (var subscriber : subscribers) {
			__subscribers.add(subscriber);
		}
	}

	public void subscribe(UserMoveEventSubscriber<T> subscriber) {
		__subscribers.add(subscriber);
	}

	public void subscribe(int order, UserMoveEventSubscriber<T> subscriber) {
		__subscribers.add(order, subscriber);
	}

	public UserMoveDto<T> broadcastCompletedEvent(T entity) {
		var userMoveResult = new UserMoveDto<T>();
		userMoveResult.setEntity(entity);
		for (var subscriber : __subscribers) {
			subscriber.onCompleted(userMoveResult);
		}
		return userMoveResult;
	}

	public void broadcastRevertedEvent(UUID todoId) {
		var userMoveResult = new UserMoveDto<T>();
		for (var subscriber : __subscribers) {
			subscriber.onReverted(userMoveResult);
		}
	}


}

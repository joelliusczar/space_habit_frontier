package space_habit_frontier.engine.services.user_moves;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import space_habit_frontier.engine.dtos.user_moves.RollDto;
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

	public void broadcastCompletedEvent(RollDto roll) {
		var userMoveResult = new UserMoveDto<T>();
		for (var subscriber : __subscribers) {
			subscriber.onCompleted(userMoveResult);
		}
	}

	public void broadcastRevertedEvent(UUID todoId) {
		var userMoveResult = new UserMoveDto<T>();
		for (var subscriber : __subscribers) {
			subscriber.onReverted(userMoveResult);
		}
	}


}

package space_habit_frontier.engine.interfaces.user_moves;

import space_habit_frontier.engine.dtos.user_moves.UserMoveDto;

public interface UserMoveEventSubscriber<T> {

	UserMoveDto<T> onCompleted(
		UserMoveDto<T> userMoveResult);

	UserMoveDto<T> onReverted(
		UserMoveDto<T> userMoveResult);
}

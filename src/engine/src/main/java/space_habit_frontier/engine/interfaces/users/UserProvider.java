package space_habit_frontier.engine.interfaces.users;

import java.util.Optional;

import space_habit_frontier.engine.dtos.users.InternalUserDto;

public interface UserProvider {
	InternalUserDto getSessionUserRequired();
	Optional<InternalUserDto> getSessionUserOptional();
	void setSessionUser(InternalUserDto user);
	boolean isLoggedIn();
	void impersonateUser(InternalUserDto user, Runnable action);
}

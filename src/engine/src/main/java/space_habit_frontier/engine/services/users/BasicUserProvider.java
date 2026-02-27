package space_habit_frontier.engine.services.users;

import java.util.Optional;

import space_habit_frontier.engine.dtos.users.InternalUserDto;
import space_habit_frontier.engine.interfaces.users.UserProvider;

public class BasicUserProvider implements UserProvider {
	private InternalUserDto sessionUser;

	public BasicUserProvider(InternalUserDto initialUser) {
		this.sessionUser = initialUser;
	}

	@Override
	public InternalUserDto getSessionUserRequired() throws IllegalStateException {
		if (sessionUser == null) {
			throw new IllegalStateException("No user is currently logged in");
		}
		return sessionUser;
	}

	@Override
	public Optional<InternalUserDto> getSessionUserOptional() {
		return Optional.ofNullable(sessionUser);
	}

	@Override
	public void setSessionUser(InternalUserDto user) {
		this.sessionUser = user;
	}

	@Override
	public boolean isLoggedIn() {
		return sessionUser != null;
	}

	@Override
	public void impersonateUser(InternalUserDto user, Runnable action) {
		InternalUserDto previousUser = this.sessionUser;
		try {
			this.sessionUser = user;
			action.run();
		} finally {
			this.sessionUser = previousUser;
		}
	}

}

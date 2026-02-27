package space_habit_frontier.engine.dtos.users;

import java.util.UUID;

import space_habit_frontier.engine.interfaces.users.ConvertableUser;

public class InternalUserDto implements ConvertableUser {
	private final UUID id;
	private final String username;
	private final String email;

	public InternalUserDto(UUID id, String username, String email) {
		this.id = id;
		this.username = username;
		this.email = email;
	}

	public UUID getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public UserDto toUserDto() {
		return new UserDto(id, username);
	}
}

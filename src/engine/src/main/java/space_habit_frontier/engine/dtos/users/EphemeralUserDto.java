package space_habit_frontier.engine.dtos.users;

import java.util.UUID;

import space_habit_frontier.engine.interfaces.users.ConvertableInternalUser;

public class EphemeralUserDto implements ConvertableInternalUser {
	private final UUID id;
	private final String username;
	private final String email;
	private final byte[] passwordHash;

	public EphemeralUserDto(UUID id,
		String username,
		String email,
		byte[] passwordHash) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.passwordHash = passwordHash;
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

	public byte[] getPasswordHash() {
		return passwordHash;
	}

	@Override
	public UserDto toUserDto() {
		return new UserDto(id, username);
	}

	@Override
	public InternalUserDto toInternalUserDto() {
		return new InternalUserDto(id, username, email);
	}
}

package space_habit_frontier.engine.dtos.web;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import space_habit_frontier.engine.dtos.users.UserDto;

public class UserSessionDto {
	private final UUID sessionId;
	private final UserDto user;
	private final ZonedDateTime expirationDateTime;

	public UserSessionDto(
			UUID sessionId,
			UserDto user,
			ZonedDateTime expirationDateTime) {
		this.sessionId = sessionId;
		this.user = user;
		this.expirationDateTime = expirationDateTime;
	}

	public UUID getSessionId() {
		return sessionId;
	}

	public ZonedDateTime getExpirationDateTime() {
		return expirationDateTime;
	}

	public UserDto getUser() {
		return user;
	}

}

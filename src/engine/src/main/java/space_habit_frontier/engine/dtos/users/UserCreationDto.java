package space_habit_frontier.engine.dtos.users;

import org.apache.commons.validator.routines.EmailValidator;
import java.lang.instrument.IllegalClassFormatException;

public class UserCreationDto {
	private final String password;
	private final String email;
	private final String username;

	public UserCreationDto(String password,
		String email,
		String username
	) throws IllegalClassFormatException {
		validateUsername(username);
		validateEmail(email);
		validatePassword(password);
		this.username = username;
		this.email = email;
		this.password = password;
	}

	private void validateUsername(String username) 
	throws IllegalClassFormatException {
		if (username == null || username.isBlank()) {
			throw new IllegalClassFormatException("Username is blank.");
		}
	}

	private void validatePassword(String password) 
	throws IllegalClassFormatException {
		if (password == null || password.length() < 6) {
			throw new IllegalClassFormatException(
				"Password must be at least 6 characters");
		}
	}

	private void validateEmail(String email) 
	throws IllegalClassFormatException {
		var validator = EmailValidator.getInstance();
		if (validator.isValid(email)) {
			throw new IllegalClassFormatException(
				"Email is invalid");
		}
	}

	public String getUsername() {
		return this.username;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}
}

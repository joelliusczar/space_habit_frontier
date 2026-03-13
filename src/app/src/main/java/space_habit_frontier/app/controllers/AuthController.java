package space_habit_frontier.app.controllers;

import space_habit_frontier.app.dtos.LoginRequest;
import space_habit_frontier.app.security.JwtService;
import space_habit_frontier.engine.dtos.users.UserCreationDto;
import space_habit_frontier.engine.dtos.users.UserDto;
import space_habit_frontier.engine.services.users.UserManagementService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	private final UserManagementService __userManagementService;
	private final AuthenticationManager __authenticationManager;

	public AuthController(
			UserManagementService userManagementService,
			AuthenticationManager authenticationManager) {
		__userManagementService = userManagementService;
		__authenticationManager = authenticationManager;
	}

	@PostMapping("/signup")
	public ResponseEntity<UserDto> addUser(@RequestBody UserCreationDto userCreationDto) {
		var createdUser = __userManagementService.addUser(userCreationDto);
		return ResponseEntity.ok(createdUser);
	}

	@PostMapping("/open-signin")
	public ResponseEntity<String> signIn(LoginRequest loginRequest) {
		var token = UsernamePasswordAuthenticationToken.unauthenticated(
			loginRequest.username(),
			loginRequest.password()
		);
		var authentication = __authenticationManager.authenticate(token);
		if (authentication.isAuthenticated()) {
			return ResponseEntity
				.ok(JwtService.generateToken(loginRequest.username()));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}

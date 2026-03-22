package space_habit_frontier.app.controllers;

import space_habit_frontier.app.dtos.LoginRequest;
import space_habit_frontier.app.security.AppCookieManager;
import space_habit_frontier.engine.dtos.users.UserCreationDto;
import space_habit_frontier.engine.dtos.users.UserDto;
import space_habit_frontier.engine.dtos.web.UserSessionDto;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.services.users.UserManagementService;
import space_habit_frontier.engine.services.web.UserSessionService;

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
	private final UserSessionService __userSessionService;
	private final AppCookieManager __appCookieManager;

	public AuthController(
			UserManagementService userManagementService,
			AuthenticationManager authenticationManager,
			UserSessionService userSessionService,
			AppCookieManager appCookieManager) {
		__userManagementService = userManagementService;
		__authenticationManager = authenticationManager;
		__userSessionService = userSessionService;
		__appCookieManager = appCookieManager;
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
		//not entirely sure of the point behind isAuthenticated since
		//authenticate is throwing an exception if the password is wrong
		if (authentication.isAuthenticated()) {
			var userSessionDto = __userSessionService
				.addSessionForUser(loginRequest.username());
			var headers = __appCookieManager.getLoginCookies(userSessionDto);
			return ResponseEntity
				.ok()
				.headers(headers)
				.body(userSessionDto.getSessionId().toString());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}

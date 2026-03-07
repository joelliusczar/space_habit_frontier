package space_habit_frontier.app.controllers;

import space_habit_frontier.app.dtos.LoginRequest;
import space_habit_frontier.engine.dtos.users.UserCreationDto;
import space_habit_frontier.engine.dtos.users.UserDto;
import space_habit_frontier.engine.services.users.UserManagementService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/users")
public class UsersController {
	private final UserManagementService __userManagementService;

	public UsersController(UserManagementService userManagementService) {
		__userManagementService = userManagementService;
	}

	@PostMapping("/signup")
	public ResponseEntity<UserDto> addUser(@RequestBody UserCreationDto userCreationDto) {
		var createdUser = __userManagementService.addUser(userCreationDto);
		return ResponseEntity.ok(createdUser);
	}

	@PostMapping("/open-signin")
	public ResponseEntity<Void> signIn(LoginRequest loginRequest) {

		return ResponseEntity.ok().build();
	}
}

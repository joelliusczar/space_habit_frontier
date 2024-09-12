package space_habit_frontier.app.controllers;

import space_habit_frontier.engine.dtos.TodoFormDto;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/todos")
public class TodosController {


	@PostMapping("")
	public TodoFormDto add(@RequestBody TodoFormDto todoFormDto) {
		return todoFormDto;
	}
}
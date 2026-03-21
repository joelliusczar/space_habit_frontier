package space_habit_frontier.app.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import space_habit_frontier.engine.services.TodoService;

@RestController
@RequestMapping("api/todos")
public class TodosController {
	private final TodoService __todoService;

	public TodosController(TodoService todoService) {
		this.__todoService = todoService;
	}

	@GetMapping("/all")
	public List<String> getAll() {
		return this.__todoService.getTodos();
	}


}

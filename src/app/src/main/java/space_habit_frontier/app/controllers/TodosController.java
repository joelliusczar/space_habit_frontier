package space_habit_frontier.app.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import space_habit_frontier.engine.dtos.TodoFormDto;
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
		var res = this.__todoService.getTodos();
		return res;
	}

	@PostMapping()
	public void add(@RequestBody TodoFormDto formDto) {
		this.__todoService.Add(formDto);
	}

}

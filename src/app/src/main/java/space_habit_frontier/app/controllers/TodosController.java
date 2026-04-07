package space_habit_frontier.app.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import space_habit_frontier.engine.dtos.todos.TodoFormDto;
import space_habit_frontier.engine.dtos.todos.TodoListDto;
import space_habit_frontier.engine.services.todos.TodoService;

@RestController
@RequestMapping("api/todos")
public class TodosController {
	private final TodoService __todoService;

	public TodosController(TodoService todoService) {
		this.__todoService = todoService;
	}

	@GetMapping("/all")
	public List<TodoListDto> getAll() {
		var res = this.__todoService.getTodos();
		return res;
	}

	@PostMapping()
	public void add(@RequestBody TodoFormDto formDto) {
		this.__todoService.Add(formDto);
	}

	@PostMapping("/complete/{todoId}")
	public void complete(@PathVariable String todoId) {
		this.__todoService.completeTodo(UUID.fromString(todoId));
	}

}

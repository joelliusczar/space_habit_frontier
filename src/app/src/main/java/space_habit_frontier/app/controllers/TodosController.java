package space_habit_frontier.app.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		this.__todoService.add(formDto);
	}

	@GetMapping("/{todoId}")
	public ResponseEntity<TodoFormDto> get(@PathVariable String todoId) {
		var res = this.__todoService.get(UUID.fromString(todoId));
		return res.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound()
			.build());
	}

	@PutMapping("/{todoId}")
	public void update(@PathVariable String todoId, @RequestBody TodoFormDto formDto) {
		this.__todoService.update(UUID.fromString(todoId), formDto);
	}

	@PostMapping("/complete/{todoId}")
	public void complete(@PathVariable String todoId) {
		this.__todoService.completeTodo(UUID.fromString(todoId));
	}

}

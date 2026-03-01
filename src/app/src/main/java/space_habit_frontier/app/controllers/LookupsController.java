package space_habit_frontier.app.controllers;

import space_habit_frontier.engine.services.LookupsService;
import space_habit_frontier.engine.dtos.Lookups;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/lookups")
public class LookupsController {

	private final LookupsService __lookupsService;

	public LookupsController(LookupsService lookupsService) {
		this.__lookupsService = lookupsService;
	}

	@GetMapping
	public Lookups get() {
		return this.__lookupsService.getLookups();
	}

}
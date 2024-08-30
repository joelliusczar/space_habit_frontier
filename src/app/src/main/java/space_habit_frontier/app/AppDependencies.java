package space_habit_frontier.app;

import space_habit_frontier.engine.services.LookupsService;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class AppDependencies {

	@Bean
	public LookupsService getLookupsService() {
		return new LookupsService();
	}
}
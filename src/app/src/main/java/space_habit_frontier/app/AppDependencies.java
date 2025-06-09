package space_habit_frontier.app;

import space_habit_frontier.engine.services.LookupsService;
import space_habit_frontier.engine.interfaces.DataContextProvider;
import space_habit_frontier.engine.services.db.connection_providers.PsqlConnectionProvider;
import space_habit_frontier.engine.services.secrets_providers.db.EnvOwnerSecretsProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class AppDependencies {

	@Bean
	public LookupsService getLookupsService() {
		return new LookupsService();
	}

	@Bean
	public DataContextProvider getApiDataContextProvider() {
		var secretsProvider = new EnvOwnerSecretsProvider();
		var dataContextProvider = new PsqlConnectionProvider(secretsProvider);
		return dataContextProvider;
	}
	
}
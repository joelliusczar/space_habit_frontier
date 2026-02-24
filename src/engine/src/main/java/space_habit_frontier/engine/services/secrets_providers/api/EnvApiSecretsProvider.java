package space_habit_frontier.engine.services.secrets_providers.api;

import space_habit_frontier.engine.interfaces.secrets_providers.ApiSecretsProvider;

public class EnvApiSecretsProvider implements ApiSecretsProvider{

	@Override
	public String apiSecretKey() {
		return System.getenv("DSF_AUTH_SECRET_KEY");
	}
	
}

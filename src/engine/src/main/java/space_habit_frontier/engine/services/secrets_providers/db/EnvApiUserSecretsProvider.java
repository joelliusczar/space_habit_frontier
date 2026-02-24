package space_habit_frontier.engine.services.secrets_providers.db;

import space_habit_frontier.engine.interfaces.secrets_providers.DbSecretsProvider;

public class EnvApiUserSecretsProvider implements DbSecretsProvider{

	@Override
	public String dbUserName() {
		return "api_user";
	}

	@Override
	public String dbPassword() {
		return System.getenv("DSF_DB_PASS_API");
	}

	@Override
	public String dbName() {
		return System.getenv("DSF_DATABASE_NAME");
	}
	
}

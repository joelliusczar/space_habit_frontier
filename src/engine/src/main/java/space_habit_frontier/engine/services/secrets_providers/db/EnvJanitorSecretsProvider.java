package space_habit_frontier.engine.services.secrets_providers.db;

import space_habit_frontier.engine.interfaces.secrets_providers.DbSecretsProvider;

public class EnvJanitorSecretsProvider implements DbSecretsProvider {

	@Override
	public String dbUserName() {
		return "janitor_user";
	}

	@Override
	public String dbPassword() {
		return System.getenv("DSF_DB_PASS_JANITOR");
	}

	@Override
	public String dbName() {
		return System.getenv("DSF_DATABASE_NAME");
	}

}

package space_habit_frontier.engine.interfaces.secrets_providers;

public interface DbSecretsProvider {
	
	String dbUserName();

	String dbPassword();

	String dbName();

}

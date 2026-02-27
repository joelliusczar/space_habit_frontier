package space_habit_frontier.engine.services.db.connection_providers;

import space_habit_frontier.engine.interfaces.secrets_providers.DbSecretsProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import space_habit_frontier.engine.interfaces.db.ConnectionProvider;
import space_habit_frontier.engine.interfaces.db.DataContextProvider;


public class PsqlConnectionProvider 
	implements DbSecretsProvider, ConnectionProvider, DataContextProvider {

	private DbSecretsProvider __dbSecretsProvider;

	public PsqlConnectionProvider(DbSecretsProvider dbSecretsProvider) {
		__dbSecretsProvider = dbSecretsProvider;
	}

	@Override
	public DSLContext getContext() throws SQLException {
		var context = DSL.using(getDbConnection(), SQLDialect.POSTGRES);
		return context;
	}

	@Override
	public Connection getDbConnection() throws SQLException {
		var conn = DriverManager.getConnection(
			"jdbc:postgresql://localhost:5432/" + __dbSecretsProvider.dbName(),
			__dbSecretsProvider.dbUserName(),
			__dbSecretsProvider.dbPassword()
		);
		return conn;
	}

	@Override
	public String dbUserName() {
		return __dbSecretsProvider.dbUserName();
	}

	@Override
	public String dbPassword() {
		return __dbSecretsProvider.dbPassword();
	}

	@Override
	public String dbName() {
		return __dbSecretsProvider.dbName();
	}
	
}

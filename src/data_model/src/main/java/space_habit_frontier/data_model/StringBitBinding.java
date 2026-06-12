package space_habit_frontier.data_model;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;

import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Converter;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

public class StringBitBinding implements Binding<String, String> {

	@Override
	public Converter<String, String> converter() {
		return new Converter<String,String>() {

			@Override
			public String from(String databaseObject) {
				return databaseObject;
			}

			@Override
			public String to(String userObject) {
				return userObject;
			}

			@Override
			public Class<String> fromType() {
				return String.class;
			}

			@Override
			public Class<String> toType() {
				return String.class;
			}
			
		};
	}

	@Override
	public void sql(BindingSQLContext<String> ctx) throws SQLException {
		if (ctx.render().paramType() == ParamType.INLINED) {
			ctx.render()
				.visit(DSL.inline(ctx.convert(converter()).value()))
				.sql("::bit(7)");
		}
		else {
			ctx
				.render()
				.sql(ctx.variable())
				.sql("::bit(7)");
		}
	}

	@Override
	public void register(BindingRegisterContext<String> ctx) throws SQLException {
		ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
	}

	@Override
	public void set(BindingSetStatementContext<String> ctx) throws SQLException {
		var value = ctx.convert(converter()).value();
		ctx.statement().setString(ctx.index(), value);
	}

	@Override
	public void set(BindingSetSQLOutputContext<String> ctx) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void get(BindingGetResultSetContext<String> ctx) throws SQLException {
		var fromDatabase = ctx.resultSet().getString(ctx.index());
		ctx.convert(converter()).value(fromDatabase);
	}

	@Override
	public void get(BindingGetStatementContext<String> ctx) throws SQLException {
		ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
	}

	@Override
	public void get(BindingGetSQLInputContext<String> ctx) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

}

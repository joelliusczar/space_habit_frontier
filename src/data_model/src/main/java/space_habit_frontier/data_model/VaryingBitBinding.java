package space_habit_frontier.data_model;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.BitSet;

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

public class VaryingBitBinding implements Binding<String, BitSet> {

	@Override
	public Converter<String, BitSet> converter() {
		return new Converter<String,BitSet>() {

			@Override
			public BitSet from(String databaseObject) {
				if (databaseObject == null) {
						return null;
				}
				var bitSet = new BitSet();
				for (int i = 0; i < databaseObject.length(); i++) {
					var c = databaseObject.charAt(i);
					if (c == '1') {
						bitSet.set(i);
					}
					else if (c != '0') {
						throw new IllegalArgumentException("Invalid character in bit string: " + c);
					}
				}
				return bitSet;
			}

			@Override
			public String to(BitSet userObject) {
				var sb = new StringBuilder();
				for (int i = 0; i < 7; i++) {
					sb.append(userObject.get(i) ? '1' : '0');
				}
				return sb.toString();
			}

			@Override
			public Class<String> fromType() {
				return String.class;
			}

			@Override
			public Class<BitSet> toType() {
				return BitSet.class;
			}
			
		};
	}

	@Override
	public void sql(BindingSQLContext<BitSet> ctx) throws SQLException {
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
	public void register(BindingRegisterContext<BitSet> ctx) throws SQLException {
		ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
	}

	@Override
	public void set(BindingSetStatementContext<BitSet> ctx) throws SQLException {
		var value = ctx.convert(converter()).value();
		ctx.statement().setString(ctx.index(), value);
	}

	@Override
	public void set(BindingSetSQLOutputContext<BitSet> ctx) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void get(BindingGetResultSetContext<BitSet> ctx) throws SQLException {
		var fromDatabase = ctx.resultSet().getString(ctx.index());
		ctx.convert(converter()).value(fromDatabase);
	}

	@Override
	public void get(BindingGetStatementContext<BitSet> ctx) throws SQLException {
		ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
	}

	@Override
	public void get(BindingGetSQLInputContext<BitSet> ctx) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

}


package space_habit_frontier.engine.services.users;

import java.util.Optional;

import org.jooq.DSLContext;

import space_habit_frontier.engine.constants.UserConditionCode;
import space_habit_frontier.data_model.db_generated.tables.Users;
import space_habit_frontier.engine.dtos.users.EphemeralUserDto;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;

public class UserAccessService {
	private final DSLContext __dbContext;
	// private final DatetimeProvider __datetimeProvider;

	public UserAccessService(DSLContext dbContext,
		DatetimeProvider datetimeProvider) {
		this.__dbContext = dbContext;
		// this.__datetimeProvider = datetimeProvider;
	}

	public Optional<EphemeralUserDto> getUserForLogin(String username) {
		return this.__dbContext.transactionResult(configuration -> {
			var ctx = configuration.dsl();
			return ctx.selectFrom(Users.USERS)
				.where(Users.USERS.CONDITIONCODE
					.eq(UserConditionCode.ACTIVE.getConditionCode()))
				.and(Users.USERS.USERNAME.eq(username))
				.fetchOptional(r -> new EphemeralUserDto(r.getId(),
				r.getUsername(),
				r.getEmail(),
				r.getHashedpw()));
		});
	}
}
package space_habit_frontier.engine.services.users;

import org.jooq.DSLContext;
import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.uuid.Generators;

import space_habit_frontier.data_model.db_generated.tables.Users;
import space_habit_frontier.engine.dtos.users.UserCreationDto;
import space_habit_frontier.engine.dtos.users.UserDto;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;

public class UserManagementService {
	private final DSLContext __dbContext;
	private final DatetimeProvider __datetimeProvider;

	public UserManagementService(DSLContext dbContext,
		DatetimeProvider datetimeProvider
	) {
		__dbContext = dbContext;
		__datetimeProvider = datetimeProvider;
	}

	public UserDto addUser(UserCreationDto formData) {
		var hashed = BCrypt.hashpw(formData.getPassword(),
			BCrypt.gensalt(12))
			.getBytes();
		var id = Generators.timeBasedEpochRandomGenerator().generate();
		var timestamp = __datetimeProvider.now().toOffsetDateTime();
		__dbContext.transaction(configuration -> {
			var ctx = configuration.dsl();
			ctx.insertInto(Users.USERS)
				.set(Users.USERS.ID, id)
				.set(Users.USERS.HASHEDPW, hashed)
				.set(Users.USERS.USERNAME, formData.getUsername())
				.set(Users.USERS.EMAIL, formData.getEmail())
				.set(Users.USERS.CREATIONTIMESTAMP, timestamp)
				.execute();
			ctx.commit();
		});

		return new UserDto(id, formData.getUsername());
	}
}

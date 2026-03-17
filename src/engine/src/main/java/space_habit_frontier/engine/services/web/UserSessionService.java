package space_habit_frontier.engine.services.web;

import java.security.GeneralSecurityException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import space_habit_frontier.engine.db_generated.tables.Usersessions;
import space_habit_frontier.engine.dtos.users.UserDto;
import space_habit_frontier.engine.dtos.web.UserSessionDto;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.interfaces.web.TrackingInfoProvider;
import space_habit_frontier.engine.services.users.UserAccessService;


public class UserSessionService {
	private final DSLContext __dbContext;
	private final DatetimeProvider __datetimeProvider;
	private final TrackingInfoProvider __trackingInfoProvider;
	private final UserAccessService __accessService;
	private final Logger __logger = LoggerFactory.getLogger(getClass());

	public UserSessionService(
			DSLContext dbContext,
			DatetimeProvider datetimeProvider,
			TrackingInfoProvider trackingInfoProvider,
			UserAccessService accessService) {
		__dbContext = dbContext;
		__datetimeProvider = datetimeProvider;
		__trackingInfoProvider = trackingInfoProvider;
		__accessService = accessService;
	}

	private void __deletePreviousSessions(
			UUID userid, long visitorId, DSLContext ctx) {
		ctx.deleteFrom(Usersessions.USERSESSIONS)
			.where(Usersessions.USERSESSIONS.USERID.eq(userid)
				.and(Usersessions.USERSESSIONS.VISITORID.eq(visitorId)))
			.execute();
	}

	public UserSessionDto addSessionForUser(
			UserDto user,
			long visitorId,
			ZonedDateTime expirationDateTime) {
		var sessionId = UUID.randomUUID();
		__dbContext.transaction(configuration -> {
			var ctx = configuration.dsl();
			__deletePreviousSessions(user.getId(), visitorId, ctx);
			ctx.insertInto(Usersessions.USERSESSIONS)
				.set(Usersessions.USERSESSIONS.ID, sessionId)
				.set(Usersessions.USERSESSIONS.USERID, user.getId())
				.set(Usersessions.USERSESSIONS.USERNAME, user.getUsername())
				.set(Usersessions.USERSESSIONS.VISITORID, visitorId)
				.set(
					Usersessions.USERSESSIONS.CREATIONTIMESTAMP,
					__datetimeProvider.now().toOffsetDateTime())
				.set(
					Usersessions.USERSESSIONS.EXPIRATIONTIMESTAMP,
					expirationDateTime.toOffsetDateTime())
				.execute();
		});
		return new UserSessionDto(sessionId, user, expirationDateTime);
	}

	public UserSessionDto addSessionForUser(UserDto user) {
		var expirationDateTime = __datetimeProvider.now();
		try {
			var visitorId = __trackingInfoProvider.getVisitorId();
			return addSessionForUser(user, visitorId, expirationDateTime);
		}
		catch (GeneralSecurityException e) {
			// Handle the exception as needed, for example, log it and use a default visitorId
			__logger.warn(
					"Error retrieving visitorId: {}, using default visitorId",
					e);
			var visitorId = -1L; // Default visitorId in case of an error
			return addSessionForUser(user, visitorId, expirationDateTime);
		}
	}

	public UserSessionDto addSessionForUser(String username) {
		var user = __accessService.getUserForLogin(username);
		if (user == null || user.isEmpty()) {
			throw new IllegalArgumentException("User not found");
		}
		return addSessionForUser(user.get().toUserDto());
	}

	public UserSessionDto getSessionDto(UUID sessionId) {
		return __dbContext.transactionResult(configuration -> {
			var ctx = configuration.dsl();
			return ctx.selectFrom(Usersessions.USERSESSIONS)
				.where(Usersessions.USERSESSIONS.ID.eq(sessionId))
				.fetchOne(r -> {
					return new UserSessionDto(
						r.get(Usersessions.USERSESSIONS.ID),
						new UserDto(
							r.get(Usersessions.USERSESSIONS.USERID),
							r.get(Usersessions.USERSESSIONS.USERNAME)
						),
						r.get(Usersessions.USERSESSIONS.EXPIRATIONTIMESTAMP)
							.toZonedDateTime()
					);
				});
		});
	}

	public List<UserSessionDto> getSessionDtos(String username) {
	return __dbContext.transactionResult(configuration -> {
			var ctx = configuration.dsl();
			return ctx.selectFrom(Usersessions.USERSESSIONS)
				.where(Usersessions.USERSESSIONS.USERNAME.eq(username))
				.fetch(r -> {
					return new UserSessionDto(
						r.get(Usersessions.USERSESSIONS.ID),
						new UserDto(
							r.get(Usersessions.USERSESSIONS.USERID),
							r.get(Usersessions.USERSESSIONS.USERNAME)
						),
						r.get(Usersessions.USERSESSIONS.EXPIRATIONTIMESTAMP)
							.toZonedDateTime()
					);
				});
		});
	}

	public boolean isSessionExpired(UserSessionDto session) {
		return __datetimeProvider.isAfterNow(session.getExpirationDateTime());
	}
	
}

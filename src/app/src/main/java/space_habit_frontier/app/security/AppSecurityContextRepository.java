package space_habit_frontier.app.security;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import space_habit_frontier.engine.dtos.web.UserSessionDto;
import space_habit_frontier.engine.services.web.UserSessionService;

public class AppSecurityContextRepository implements SecurityContextRepository {
	private final ConcurrentMap<String, UserSessionDto> __inMemSessionsMap;
	private final UserSessionService __userSessionService;
	private final SecurityContextHolderStrategy __securityContextHolderStrategy;

	public AppSecurityContextRepository(
			ConcurrentMap<String, UserSessionDto> inMemSessionsMap,
			UserSessionService userSessionService,
			SecurityContextHolderStrategy securityContextHolderStrategy) {
		this.__inMemSessionsMap = inMemSessionsMap;
		this.__userSessionService = userSessionService;
		this.__securityContextHolderStrategy = securityContextHolderStrategy;
	}


	private SecurityContext loadContextInternal(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("authorization");
		UserSessionDto userSessionDto = null;

		if (authorizationHeader != null 
			&& authorizationHeader.startsWith("Session ")) {
			var token = authorizationHeader.substring(8);
			userSessionDto = __inMemSessionsMap.computeIfAbsent(
				token, (key) -> {
					var session = __userSessionService
						.getSessionDto(UUID.fromString(key));
					if (session != null) {
						return session;
					}
					else {
						return null;
					}
				});
		}

		if (userSessionDto != null
			&& !__userSessionService.isSessionExpired(userSessionDto)) {
				var auth = new UsernamePasswordAuthenticationToken(
					userSessionDto.getUser(),
					null,
					List.of());
					var context = this.__securityContextHolderStrategy.createEmptyContext();
					context.setAuthentication(auth);
					return context;
		}

		return this.__securityContextHolderStrategy.createEmptyContext();
	}

	@Override
	public SecurityContext loadContext(
			@SuppressWarnings("deprecation")
			HttpRequestResponseHolder requestResponseHolder) {
		return loadContextInternal(requestResponseHolder.getRequest());
	}

	@Override
	public DeferredSecurityContext loadDeferredContext(
			HttpServletRequest request) {
		return new SupplierDeferredSecurityContext(
			() -> loadContextInternal(request));
	}

	@Override
	public void saveContext(SecurityContext context,
			HttpServletRequest request,
			HttpServletResponse response) {
			// No-op since we're using a deferred context and the actual loading is handled in loadContextInternal
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		var context = loadContextInternal(request);
		return context != null;
	}
	
}
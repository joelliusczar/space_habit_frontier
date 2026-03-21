package space_habit_frontier.app.filters;

import java.io.IOException;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.services.web.UserSessionService;


@Component
public class CookieFilter extends OncePerRequestFilter {
	private final UserDetailsService __userDetailsService;
	private final UserSessionService __userSessionService;
	private final DatetimeProvider __datetimeProvider;

	public CookieFilter(
			UserDetailsService userDetailsService,
			UserSessionService userSessionService,
			DatetimeProvider datetimeProvider
	) {
		super();
		this.__userDetailsService = userDetailsService;
		this.__userSessionService = userSessionService;
		this.__datetimeProvider = datetimeProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			filterChain.doFilter(request, response);
			return;
		}
		var sessionIdGuess = "";
		var authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			sessionIdGuess = authHeader.substring(8);
		}
		else {
			var sessionIdCookie = WebUtils.getCookie(request, "session_token");
			if (sessionIdCookie == null) {
				filterChain.doFilter(request, response);
				return;
			}
			sessionIdGuess = sessionIdCookie.getValue();
		}

		var sessionId = UUID.fromString(sessionIdGuess);
		var userSessionDto = __userSessionService.getSessionDto(sessionId);
		if (userSessionDto != null) {
			var isExpired = __datetimeProvider
				.isBeforeNow(userSessionDto.getExpirationDateTime());
			if (isExpired) {
				__userSessionService.deleteSession(sessionId);
				response.addHeader("x-authexpired", "true");
				filterChain.doFilter(request, response);
				return;
			}
			var userDetails = __userDetailsService.loadUserByUsername(
				userSessionDto.getUser().getUsername());
				var authToken = new UsernamePasswordAuthenticationToken(userDetails, 
					null, 
					userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		filterChain.doFilter(request, response);
	}
}
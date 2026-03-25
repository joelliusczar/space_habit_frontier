package space_habit_frontier.app.helpers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestHelpers {

	public static Optional<UUID> ExtractSessionId(HttpServletRequest request) {
		var sessionIdGuess = "";
		var authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			sessionIdGuess = authHeader.substring(8);
		}
		else {
			var sessionIdCookie = WebUtils.getCookie(request, "session_token");
			if (sessionIdCookie == null) {
				return Optional.empty();
			}
			sessionIdGuess = sessionIdCookie.getValue();
		}

		var sessionId = UUID.fromString(sessionIdGuess);
		return Optional.of(sessionId);
	}
}

package space_habit_frontier.app.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import space_habit_frontier.app.security.JwtService;

@Component
public class JwtFilter extends OncePerRequestFilter {
	private final UserDetailsService __userDetailsService;

	public JwtFilter(UserDetailsService userDetailsService) {
		super();
		this.__userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		var authHeader = request.getHeader("Authorization");
		var token = "";
		var username = "";
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = JwtService.extractUsername(token);
		}

		if (username.length() > 0 
		&& SecurityContextHolder.getContext().getAuthentication() == null) {
			var userDetails = __userDetailsService.loadUserByUsername(username);
			if (JwtService.validateToken(token, userDetails.getUsername())) {
				var authToken = new UsernamePasswordAuthenticationToken(userDetails, 
					null, 
					userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
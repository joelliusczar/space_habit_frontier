package space_habit_frontier.app.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import space_habit_frontier.app.dtos.AppUserDetails;
import space_habit_frontier.engine.services.users.UserAccessService;

public class AppUserDetailsService implements UserDetailsService {

	private final UserAccessService __userAccessService;

	public AppUserDetailsService(UserAccessService userAccessService) {
		__userAccessService = userAccessService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = __userAccessService.getUserForLogin(username);
		if (user == null || user.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		return new AppUserDetails(user.get());
	}

}

package space_habit_frontier.app.dtos;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import space_habit_frontier.engine.dtos.users.EphemeralUserDto;

public class AppUserDetails implements UserDetails {

	private final EphemeralUserDto __userDto;

	public AppUserDetails(EphemeralUserDto userDto) {
		__userDto = userDto;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public @Nullable String getPassword() {
		return new String(__userDto.getPasswordHash(), StandardCharsets.UTF_8);
	}

	@Override
	public String getUsername() {
		return __userDto.getUsername();
	}
	
}

package space_habit_frontier.app.dtos;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import space_habit_frontier.engine.dtos.users.EphemeralUserDto;
import space_habit_frontier.engine.dtos.users.InternalUserDto;
import space_habit_frontier.engine.dtos.users.UserDto;
import space_habit_frontier.engine.interfaces.users.ConvertableInternalUser;

public class AppUserDetails implements UserDetails, ConvertableInternalUser {

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

	@Override
	public UserDto toUserDto() {
		return __userDto.toUserDto();
	}

	@Override
	public InternalUserDto toInternalUserDto() {
		return __userDto.toInternalUserDto();
	}
	
}

package space_habit_frontier.engine.interfaces.users;

import space_habit_frontier.engine.dtos.users.InternalUserDto;

public interface ConvertableInternalUser extends ConvertableUser{
	InternalUserDto toInternalUserDto();
}

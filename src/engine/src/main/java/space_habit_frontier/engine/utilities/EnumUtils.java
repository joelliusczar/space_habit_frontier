package space_habit_frontier.engine.utilities;

import space_habit_frontier.engine.interfaces.FriendlyNameable;
import space_habit_frontier.engine.dtos.NamedIntId;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumUtils {
	public static <E extends Enum<E> & FriendlyNameable> List<NamedIntId>
	getNamedIds(Class<E> e)
	{
		return Arrays.stream(e.getEnumConstants())
			.map((var c) -> new NamedIntId(c.ordinal(), c.getFriendlyName()))
			.collect(Collectors.toList());
	}
}
package space_habit_frontier.engine.interfaces.dates;

import java.time.ZonedDateTime;

public interface DatetimeProvider {
	ZonedDateTime now();
	ZonedDateTime nowUtc();
}
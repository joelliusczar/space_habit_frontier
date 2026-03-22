package space_habit_frontier.engine.interfaces.dates;

import java.time.ZonedDateTime;

public interface ExpirationDatesProvider {
	ZonedDateTime getLoginExpiration();
}

package space_habit_frontier.engine.services.dates;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import space_habit_frontier.engine.interfaces.dates.ExpirationDatesProvider;

public class DefaultExpirationDatesProvider implements ExpirationDatesProvider{

	@Override
	public ZonedDateTime getLoginExpiration() {
		return ZonedDateTime.of(
			2050,
			1, 
			1, 
			0, 
			0, 
			0, 
			0, 
			ZoneId.of("UTC"));
	}
	
}

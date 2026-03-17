package space_habit_frontier.engine.services.dates;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;

public class DefaultDatetimeProvider implements DatetimeProvider {

	@Override
	public ZonedDateTime now() {
		return ZonedDateTime.now();
	}

	@Override
	public ZonedDateTime nowUtc() {
		return ZonedDateTime.now(ZoneOffset.UTC);
	}

	@Override
	public boolean isBeforeNow(ZonedDateTime dt1) {
		return dt1.isBefore(now());
	}

	@Override
	public boolean isAfterNow(ZonedDateTime dt1) {
		return dt1.isAfter(now());
	}
	
	
}

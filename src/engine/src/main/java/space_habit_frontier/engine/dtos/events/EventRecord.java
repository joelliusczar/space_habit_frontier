package space_habit_frontier.engine.dtos.events;

import java.time.ZonedDateTime;

public record EventRecord(String id, 
		String userId, 
		String action, 
		long visitorId,
		ZonedDateTime datetimeUtc, 
		String keypath, 
		String sphere, 
		String url, 
		String method, 
		String extraInfo)
{}


package space_habit_frontier.engine.dtos.events;

import java.time.OffsetDateTime;

public record EventRecord(String id, 
		String userId, 
		String action, 
		long visitorId,
		OffsetDateTime datetimeUtc, 
		String keypath, 
		String sphere, 
		String url, 
		String method, 
		String extraInfo)
{}


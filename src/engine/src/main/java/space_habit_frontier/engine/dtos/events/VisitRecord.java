package space_habit_frontier.engine.dtos.events;

import java.time.OffsetDateTime;

public record VisitRecord(String id, 
	long visitorId, 
	OffsetDateTime timestamp, 
	String urlPath, 
	String method, 
	String extraInfo) 
{}


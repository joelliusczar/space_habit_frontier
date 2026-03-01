package space_habit_frontier.engine.dtos.events;

import java.time.ZonedDateTime;

public record VisitRecord(String id, 
	long visitorId, 
	ZonedDateTime timestamp, 
	String urlPath, 
	String method, 
	String extraInfo) 
{}


package space_habit_frontier.engine.interfaces.events;


import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import space_habit_frontier.engine.dtos.events.EventRecord;
import space_habit_frontier.engine.dtos.events.VisitRecord;

public interface EventQueryer {
	Stream<VisitRecord> getVisitRecords();
	Stream<VisitRecord> getVisitRecords(ZonedDateTime from);
	Stream<VisitRecord> getVisitRecords(ZonedDateTime from, long visitorId);
	Stream<VisitRecord> getVisitRecords(ZonedDateTime from,
		long visitorId,
		String url);
	Stream<EventRecord> getEventRecords();
	Stream<EventRecord> getEventRecords(ZonedDateTime from);
	Stream<EventRecord> getEventRecords(ZonedDateTime from, UUID userId);
	Stream<EventRecord> getEventRecords(ZonedDateTime from,
		UUID userId, 
		String sphere);
	Stream<EventRecord> getEventRecords(ZonedDateTime from,
		UUID userId, 
		String sphere,
		String keypath);
	Stream<EventRecord> getEventRecords(ZonedDateTime from,
		UUID userId, 
		String sphere,
		String keypath,
		String action);
}
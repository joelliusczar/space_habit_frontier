package space_habit_frontier.engine.interfaces.events;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import space_habit_frontier.engine.dtos.events.EventRecord;
import space_habit_frontier.engine.dtos.events.VisitRecord;

public interface EventQueryer {
	Stream<VisitRecord> getVisitRecords(long visitorId, 
		OffsetDateTime from,
		String url,
		long limit);
	Stream<EventRecord> getEventRecords(UUID userId, 
		OffsetDateTime from,
		String action,
		String sphere,
		String keypath,
		long limit);
}
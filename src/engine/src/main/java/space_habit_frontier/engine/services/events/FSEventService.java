package space_habit_frontier.engine.services.events;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import space_habit_frontier.engine.dtos.events.EventRecord;
import space_habit_frontier.engine.dtos.events.VisitRecord;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.interfaces.events.EventLogger;
import space_habit_frontier.engine.interfaces.events.EventQueryer;
import space_habit_frontier.engine.interfaces.users.UserProvider;
import space_habit_frontier.engine.interfaces.web.TrackingInfoProvider;

public class FSEventService implements EventQueryer, EventLogger {

	@Override
	public Stream<VisitRecord> getVisitRecords(long visitorId, OffsetDateTime from, String url, long limit) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getVisitRecords'");
	}

	@Override
	public Stream<EventRecord> getEventRecords(UUID userId, OffsetDateTime from, String action, String sphere,
			String keypath, long limit) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getEventRecords'");
	}

	@Override
	public DatetimeProvider getDatetimeProvider() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getDatetimeProvider'");
	}

	@Override
	public UserProvider getUserProvider() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getUserProvider'");
	}

	@Override
	public TrackingInfoProvider getTrackingInfoProvider() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getTrackingInfoProvider'");
	}

	@Override
	public void addEventRecord(EventRecord record) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addEventRecord'");
	}

	@Override
	public void addVisitRecord(VisitRecord record) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addVisitRecord'");
	}
	
}

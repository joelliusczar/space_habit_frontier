package space_habit_frontier.engine.services.events;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import space_habit_frontier.engine.dtos.events.EventRecord;
import space_habit_frontier.engine.dtos.events.VisitRecord;
import space_habit_frontier.engine.dtos.events.event_maps.UserIdEventMap;
import space_habit_frontier.engine.dtos.events.event_maps.VisitorVisitMap;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.interfaces.events.EventLogger;
import space_habit_frontier.engine.interfaces.events.EventQueryer;
import space_habit_frontier.engine.interfaces.users.UserProvider;
import space_habit_frontier.engine.interfaces.web.TrackingInfoProvider;

public class InMemEventService implements EventLogger, EventQueryer {

	private final DatetimeProvider datetimeProvider;
	private final UserProvider userProvider;
	private final TrackingInfoProvider trackingInfoProvider;
	private final VisitorVisitMap visitorVisitMap;
	private final UserIdEventMap userIdEventMap;

	public InMemEventService(VisitorVisitMap visitorVisitMap,
		UserIdEventMap userIdEventMap,
		DatetimeProvider datetimeProvider,
		UserProvider userProvider,
		TrackingInfoProvider trackingInfoProvider) {

		this.datetimeProvider = datetimeProvider;
		this.userProvider = userProvider;
		this.trackingInfoProvider = trackingInfoProvider;
		this.visitorVisitMap = visitorVisitMap;
		this.userIdEventMap = userIdEventMap;
	}

	@Override
	public DatetimeProvider getDatetimeProvider() {
		return datetimeProvider;
	}

	@Override
	public UserProvider getUserProvider() {
		return userProvider;
	}

	@Override
	public TrackingInfoProvider getTrackingInfoProvider() {
		return trackingInfoProvider;
	}

	@Override
	public void addEventRecord(EventRecord record) {
		this.userIdEventMap.add(record);
	}

	@Override
	public void addVisitRecord(VisitRecord record) {
		this.visitorVisitMap.add(record);
	}

	@Override
	public Stream<VisitRecord> getVisitRecords() {
		return this.visitorVisitMap.getAll();
	}

	@Override
	public Stream<VisitRecord> getVisitRecords(ZonedDateTime from) {
		return this.visitorVisitMap.getAll()
			.filter(r -> r.timestamp().isAfter(from));
	}

	@Override
	public Stream<VisitRecord> getVisitRecords(ZonedDateTime from,
		long visitorId
	) {
		return this.visitorVisitMap
			.subMap(String.valueOf(visitorId))
			.getAll()
			.filter(r -> r.timestamp().isAfter(from));
	}

	@Override
	public Stream<VisitRecord> getVisitRecords(ZonedDateTime from,
		long visitorId,
		String url
	) {
		var res = this.visitorVisitMap
			.subMap(String.valueOf(visitorId))
			.subMap(url)
			.getAll()
			.filter(r -> r.timestamp().isAfter(from));
		return res;
	}

	@Override
	public Stream<EventRecord> getEventRecords() {
		return this.userIdEventMap
			.getAll();
	}

	@Override
	public Stream<EventRecord> getEventRecords(ZonedDateTime from) {
		return this.userIdEventMap
			.getAll()
			.filter(r -> r.datetimeUtc().isAfter(from));
	}

	@Override
	public Stream<EventRecord> getEventRecords(ZonedDateTime from, UUID userId) {
		return this.userIdEventMap
			.subMap(String.valueOf(userId))
			.getAll()
			.filter(r -> r.datetimeUtc().isAfter(from));
	}

	@Override
	public Stream<EventRecord> getEventRecords(ZonedDateTime from,
		UUID userId,
		String sphere
	) {
		return this.userIdEventMap
			.subMap(String.valueOf(userId))
			.subMap(sphere)
			.getAll()
			.filter(r -> r.datetimeUtc().isAfter(from));
	}

	@Override
	public Stream<EventRecord> getEventRecords(ZonedDateTime from,
		UUID userId,
		String sphere,
		String keypath
	) {
		return this.userIdEventMap
			.subMap(String.valueOf(userId))
			.subMap(sphere)
			.subMap(keypath)
			.getAll()
			.filter(r -> r.datetimeUtc().isAfter(from));
	}

	@Override
	public Stream<EventRecord> getEventRecords(ZonedDateTime from,
		UUID userId,
		String sphere,
		String action, 
		String keypath
	) {
		return this.userIdEventMap
			.subMap(String.valueOf(userId))
			.subMap(sphere)
			.subMap(keypath)
			.subMap(action)
			.getAll()
			.filter(r -> r.datetimeUtc().isAfter(from));
	}
	
}

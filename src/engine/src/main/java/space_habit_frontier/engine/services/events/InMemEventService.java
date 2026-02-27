package space_habit_frontier.engine.services.events;

import java.time.OffsetDateTime;
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
	public Stream<VisitRecord> getVisitRecords(
		long visitorId,
		OffsetDateTime from,
		String url,
		long limit) {

		var res = this.visitorVisitMap.subMap(String.valueOf(visitorId))
			.subMap(url)
			.getAll()
			.filter(r -> r.timestamp().isAfter(from))
			.limit(limit);
		return res;
	}

	@Override
	public Stream<EventRecord> getEventRecords(
		UUID userId,
		OffsetDateTime from,
		String action, 
		String sphere,
		String keypath, 
		long limit) {

		return this.userIdEventMap
			.subMap(String.valueOf(userId))
			.subMap(sphere)
			.subMap(keypath)
			.subMap(action)
			.getAll()
			.filter(r -> r.datetimeUtc().isAfter(from))
			.limit(limit);
	}
	
}

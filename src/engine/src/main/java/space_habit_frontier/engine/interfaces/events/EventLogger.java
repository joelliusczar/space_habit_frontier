package space_habit_frontier.engine.interfaces.events;

import java.security.GeneralSecurityException;
import java.util.UUID;

import space_habit_frontier.engine.dtos.events.EventRecord;
import space_habit_frontier.engine.dtos.events.VisitRecord;
import space_habit_frontier.engine.interfaces.dates.DatetimeProviderHolder;
import space_habit_frontier.engine.interfaces.users.UserProviderHolder;
import space_habit_frontier.engine.interfaces.web.TrackingInfoProviderHolder;

public interface EventLogger 
	extends DatetimeProviderHolder, 
	UserProviderHolder, 
	TrackingInfoProviderHolder {

	void addEventRecord(EventRecord record);
	void addVisitRecord(VisitRecord record);

	default EventRecord createEventRecord(String action,
		String sphere,
		String keypath,
		String extraInfo) throws GeneralSecurityException {

		var userId = getUserProvider().getSessionUserRequired().getId();
		var visitorId = getTrackingInfoProvider().getVisitorId();
		var urlPath = getTrackingInfoProvider().getTrackingInfo().url();
		var dt = getDatetimeProvider().now();

		return new EventRecord(
			UUID.randomUUID().toString(),
			userId.toString(),
			action,
			visitorId,
			dt,
			keypath,
			sphere,
			urlPath,
			getTrackingInfoProvider().getTrackingInfo().method(),
			extraInfo
		);
	}

	default VisitRecord createVisitRecord(String extraInfo) 
		throws GeneralSecurityException {
		var urlPath = getTrackingInfoProvider().getTrackingInfo().url();
		var method = getTrackingInfoProvider().getTrackingInfo().method();
		var visitorId = getTrackingInfoProvider().getVisitorId();
		var dt = getDatetimeProvider().now();

		return new VisitRecord(
			UUID.randomUUID().toString(),
			visitorId,
			dt,
			urlPath,
			method,
			extraInfo
		);
	}
}

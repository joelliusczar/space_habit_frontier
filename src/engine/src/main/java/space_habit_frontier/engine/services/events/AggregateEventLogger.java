package space_habit_frontier.engine.services.events;


import java.util.Arrays;
import java.util.List;

import space_habit_frontier.engine.dtos.events.EventRecord;
import space_habit_frontier.engine.dtos.events.VisitRecord;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.interfaces.events.EventLogger;
import space_habit_frontier.engine.interfaces.users.UserProvider;
import space_habit_frontier.engine.interfaces.web.TrackingInfoProvider;

public class AggregateEventLogger implements EventLogger{

	private final DatetimeProvider __DatetimeProvider;
	private final UserProvider __userProvider;
	private final TrackingInfoProvider __trackingInfoProvider;
	private final List<EventLogger> __eventLoggers;

	public AggregateEventLogger(DatetimeProvider datetimeProvider,
		UserProvider userProvider,
		TrackingInfoProvider trackingInfoProvider,
		EventLogger ...eventLoggers) {
		__DatetimeProvider = datetimeProvider;
		__trackingInfoProvider = trackingInfoProvider;
		__userProvider = userProvider;
		__eventLoggers = Arrays.asList(eventLoggers);
	}

	@Override
	public DatetimeProvider getDatetimeProvider() {
		return __DatetimeProvider;
	}

	@Override
	public UserProvider getUserProvider() {
		return __userProvider;
	}

	@Override
	public TrackingInfoProvider getTrackingInfoProvider() {
		return __trackingInfoProvider;
	}

	@Override
	public void addEventRecord(EventRecord record) {
		for (var dest : __eventLoggers) {
			dest.addEventRecord(record);
		}
	}

	@Override
	public void addVisitRecord(VisitRecord record) {
		for (var dest : __eventLoggers) {
			dest.addVisitRecord(record);
		}
	}
	
}

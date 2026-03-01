package space_habit_frontier.engine.services.events;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Stream;

import space_habit_frontier.engine.dtos.events.EventRecord;
import space_habit_frontier.engine.dtos.events.VisitRecord;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.interfaces.events.EventLogger;
import space_habit_frontier.engine.interfaces.events.EventQueryer;
import space_habit_frontier.engine.interfaces.users.UserProvider;
import space_habit_frontier.engine.interfaces.web.TrackingInfoProvider;
import space_habit_frontier.engine.services.environment.EnvironmentWrapper;
import tools.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class FSEventService implements EventQueryer, EventLogger {

	private final DatetimeProvider __datetimeProvider;
	private final UserProvider __userProvider;
	private final TrackingInfoProvider __trackingInfoProvider;
	private static final Logger __logger = LoggerFactory
		.getLogger(FSEventService.class);

	public FSEventService(DatetimeProvider datetimeProvider,
		UserProvider userProvider,
		TrackingInfoProvider trackingInfoProvider) {

		this.__datetimeProvider = datetimeProvider;
		this.__userProvider = userProvider;
		this.__trackingInfoProvider = trackingInfoProvider;
	}

	public DateTimeFormatter getDateTimeFormatter() {
		return DateTimeFormatter.ofPattern("y-D_H");
	}

	private Path getEventLogPath() {
		var dt = __datetimeProvider.nowUtc();
		var path = Paths.get(EnvironmentWrapper.getEventLogDirectory(), 
			String.format("events_shf_%s.jsonl", 
				getDateTimeFormatter().format(dt)));
		return path;
	}

	private Path getVisitLogPath() {
		var dt = __datetimeProvider.nowUtc();
		var path = Paths.get(EnvironmentWrapper.getVisitLogDirectory(), 
			String.format("visits_shf_%s.jsonl", 
				getDateTimeFormatter().format(dt)));
		return path;
	}


	private Stream<EventRecord> loadMostRecentEvents(int hoursAgo) {
		var mapper = JsonMapper.builder()
			.build();
		try {
			var res = Files.list(getEventLogPath()).limit(hoursAgo).flatMap(log -> {
				try {
					return Files.readAllLines(log.toAbsolutePath())
						.stream()
						.map(line -> {
							return mapper.readValue(line, EventRecord.class);
						});
				}
				catch(IOException ioex) {
					__logger.error("Error occured loading events in lambda: {}",
						ioex);
						return Stream.empty();
				}
			});
			return res;
		}
		catch(IOException ioex) {
			__logger.error("Error occured loading events: {}", ioex);
			return Stream.empty();
		}
	}

	private Stream<VisitRecord> loadMostRecentVisits(int hoursAgo) {
		var mapper = JsonMapper.builder()
			.build();
		try {
			var res = Files.list(getEventLogPath()).limit(hoursAgo).flatMap(log -> {
				try {
					return Files.readAllLines(log.toAbsolutePath())
						.stream()
						.map(line -> {
							return mapper.readValue(line, VisitRecord.class);
						});
				}
				catch(IOException ioex) {
					__logger.error("Error occured loading events in lambda: {}",
						ioex);
						return Stream.empty();
				}
			});
			return res;
		}
		catch(IOException ioex) {
			__logger.error("Error occured loading events: {}", ioex);
			return Stream.empty();
		}
	}

	@Override
	public Stream<VisitRecord> getVisitRecords() {
		return loadMostRecentVisits(24);
	}

	@Override
	public Stream<VisitRecord> getVisitRecords(ZonedDateTime from) {
		return getVisitRecords()
			.filter(e -> e.timestamp().isAfter(from));
	}

	@Override
	public Stream<VisitRecord> getVisitRecords(ZonedDateTime from,
		long visitorId
	) {
		return getVisitRecords(from).filter(e -> e.visitorId() == visitorId);
	}

	@Override
	public Stream<VisitRecord> getVisitRecords(ZonedDateTime from,
		long visitorId,
		String url
	) {
		return getVisitRecords(from, visitorId)
			.filter(e -> e.urlPath().equals(url));
	}

	@Override
	public Stream<EventRecord> getEventRecords() {
		return loadMostRecentEvents(24);
	}

	@Override
	public Stream<EventRecord> getEventRecords(ZonedDateTime from) {
		return getEventRecords().filter(e -> e.datetimeUtc().isAfter(from));
	}

	@Override
	public Stream<EventRecord> getEventRecords(ZonedDateTime from, UUID userId) {
		return getEventRecords(from)
			.filter(e -> e.userId().equals(userId.toString()));
	}

	@Override
	public Stream<EventRecord> getEventRecords(ZonedDateTime from,
		UUID userId,
		String sphere) {
		return getEventRecords(from, userId)
			.filter(e -> e.sphere().equals(sphere));
	}

	@Override
	public Stream<EventRecord> getEventRecords(ZonedDateTime from,
		UUID userId,
		String sphere,
		String keypath) {
		return getEventRecords(from, userId, sphere)
			.filter(e -> e.keypath().equals(keypath));
	}

	@Override
	public Stream<EventRecord> getEventRecords(ZonedDateTime from,
		UUID userId,
		String sphere,
		String keypath,
		String action) {
		return getEventRecords(from, userId, sphere)
			.filter(e -> 
				e.keypath().equals(keypath));
	}

	@Override
	public DatetimeProvider getDatetimeProvider() {
		return __datetimeProvider;
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
		var mapper = JsonMapper.builder()
			.build();
		var json = mapper.writeValueAsString(record);
		try {
			Files.writeString(getEventLogPath(), json, StandardOpenOption.APPEND);
		}
		catch(IOException ioex) {
			__logger.error("Error occured logging event: {}", ioex);
		}
	}

	@Override
	public void addVisitRecord(VisitRecord record) {
		var mapper = JsonMapper.builder()
			.build();
		var json = mapper.writeValueAsString(record);
		try {
			Files.writeString(getVisitLogPath(), json, StandardOpenOption.APPEND);
		}
		catch(IOException ioex) {
			__logger.error("Error occured logging visit: {}", ioex);
		}
	}
	
}

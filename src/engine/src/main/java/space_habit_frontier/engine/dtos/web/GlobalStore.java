package space_habit_frontier.engine.dtos.web;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import space_habit_frontier.engine.dtos.events.event_maps.UserIdEventMap;
import space_habit_frontier.engine.dtos.events.event_maps.VisitorVisitMap;

public class GlobalStore {
	private static GlobalStore instance;
	private ConcurrentMap<String, Map<String, Long>> visitorIdMap;
	private ConcurrentMap<Integer, Map<String, Double>> timeoutBucket;
	private VisitorVisitMap visitorVisitMap;
	private UserIdEventMap userIdEventMap;

	private GlobalStore() {
		this.visitorIdMap = new ConcurrentHashMap<>();
		this.timeoutBucket = new ConcurrentHashMap<>();
		this.visitorVisitMap = new VisitorVisitMap();
		this.userIdEventMap = new UserIdEventMap();
		// Private constructor to prevent instantiation
	}

	public static synchronized GlobalStore getInstance() {
		if (instance == null) {
			instance = new GlobalStore();
		}
		return instance;
	}

	public ConcurrentMap<String, Map<String, Long>> getVisitorIdMap() {
		return visitorIdMap;
	}

	public ConcurrentMap<Integer, Map<String, Double>> getTimeoutBucket() {
		return timeoutBucket;
	}

	public VisitorVisitMap getVisitorVisitMap() {
		return visitorVisitMap;
	}

	public UserIdEventMap getUserIdEventMap() {
		return userIdEventMap;
	}
}

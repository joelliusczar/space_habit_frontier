package space_habit_frontier.engine.dtos.web;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import space_habit_frontier.engine.dtos.events.event_maps.UserIdEventMap;
import space_habit_frontier.engine.dtos.events.event_maps.VisitorVisitMap;

public class GlobalStore {
	private static GlobalStore instance;
	private final ConcurrentMap<String, Map<String, Long>> __visitorIdMap;
	private final ConcurrentMap<Integer, Map<String, Double>> __timeoutBucket;
	private final VisitorVisitMap __visitorVisitMap;
	private final UserIdEventMap __userIdEventMap;
	private final ConcurrentMap<String, UserSessionDto> __userSessionMap;;

	private GlobalStore() {
		this.__visitorIdMap = new ConcurrentHashMap<>();
		this.__timeoutBucket = new ConcurrentHashMap<>();
		this.__visitorVisitMap = new VisitorVisitMap();
		this.__userIdEventMap = new UserIdEventMap();
		this.__userSessionMap = new ConcurrentHashMap<>();
		// Private constructor to prevent instantiation
	}

	public static synchronized GlobalStore getInstance() {
		if (instance == null) {
			instance = new GlobalStore();
		}
		return instance;
	}

	public ConcurrentMap<String, Map<String, Long>> getVisitorIdMap() {
		return __visitorIdMap;
	}

	public ConcurrentMap<Integer, Map<String, Double>> getTimeoutBucket() {
		return __timeoutBucket;
	}

	public VisitorVisitMap getVisitorVisitMap() {
		return __visitorVisitMap;
	}

	public UserIdEventMap getUserIdEventMap() {
		return __userIdEventMap;
	}

	public ConcurrentMap<String, UserSessionDto> getUserSessionMap() {
		return __userSessionMap;
	}
}

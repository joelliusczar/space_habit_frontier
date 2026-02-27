package space_habit_frontier.engine.dtos.events.event_maps;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import space_habit_frontier.engine.dtos.events.EventRecord;
import space_habit_frontier.engine.interfaces.events.EventMap;

public class UserIdEventMap implements EventMap<EventRecord> {
	private ConcurrentMap<String, SphereEventMap> userIdMap;

	public UserIdEventMap() {
		this.userIdMap = new ConcurrentHashMap<>();
	}

	@Override
	public SphereEventMap subMap(String key) {
		return userIdMap.getOrDefault(key, new SphereEventMap());
	}

	@Override
	public Stream<EventRecord> getAll() {
		return userIdMap
			.values()
			.stream()
			.flatMap(SphereEventMap::getAll);
	}

	@Override
	public void add(EventRecord record) {
		userIdMap
			.computeIfAbsent(record.userId(), k -> new SphereEventMap())
			.add(record);
	}

}

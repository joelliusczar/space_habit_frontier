package space_habit_frontier.engine.dtos.events.event_maps;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import space_habit_frontier.engine.dtos.events.EventRecord;
import space_habit_frontier.engine.interfaces.events.EventMap;

public class SphereEventMap implements EventMap<EventRecord> {
	private Map<String, KeypathEventMap> sphereMap;

	public SphereEventMap() {
		this.sphereMap = new HashMap<>();
	}

	@Override
	public KeypathEventMap subMap(String key) {
		return sphereMap
			.getOrDefault(key, new KeypathEventMap());
	}

	@Override
	public Stream<EventRecord> getAll() {
		return sphereMap
			.values()
			.stream()
			.flatMap(KeypathEventMap::getAll);
	}

	@Override
	public void add(EventRecord record) {
		sphereMap
			.computeIfAbsent(record.sphere(), k -> new KeypathEventMap())
			.add(record);
	}
}

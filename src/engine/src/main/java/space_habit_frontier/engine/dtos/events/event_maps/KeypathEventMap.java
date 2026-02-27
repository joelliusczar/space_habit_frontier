package space_habit_frontier.engine.dtos.events.event_maps;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import space_habit_frontier.engine.dtos.events.EventRecord;
import space_habit_frontier.engine.interfaces.events.EventMap;

public class KeypathEventMap implements EventMap<EventRecord> {
	private Map<String, ActionsEventMap> keypathMap;

	public KeypathEventMap() {
		this.keypathMap = new HashMap<>();
	}

	@Override
	public ActionsEventMap subMap(String key) {
		return this.keypathMap.getOrDefault(key, new ActionsEventMap());
	}

	@Override
	public Stream<EventRecord> getAll() {
		return keypathMap
			.values()
			.stream()
			.flatMap(ActionsEventMap::getAll);
	}

	@Override
	public void add(EventRecord record) {
		keypathMap
		.computeIfAbsent(record.keypath(), k -> new ActionsEventMap())
		.add(record);
	}
}

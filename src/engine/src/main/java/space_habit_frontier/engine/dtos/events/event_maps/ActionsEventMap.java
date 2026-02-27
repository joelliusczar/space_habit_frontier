package space_habit_frontier.engine.dtos.events.event_maps;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import space_habit_frontier.engine.dtos.events.EventRecord;
import space_habit_frontier.engine.interfaces.events.EventMap;


public class ActionsEventMap implements EventMap<EventRecord> {
	private Map<String, EventMapBottom<EventRecord>> urlMap;

	public ActionsEventMap() {
		this.urlMap = new HashMap<>();
	}

	@Override
	public EventMapBottom<EventRecord> subMap(String key) {
		return this.urlMap.getOrDefault(key, new EventMapBottom<>());
	}


	@Override
	public Stream<EventRecord> getAll() {
		return urlMap
			.values()
			.stream()
			.flatMap(EventMapBottom::getAll);
	}

	@Override
	public void add(EventRecord record) {
		urlMap.computeIfAbsent(record.action(), k -> new EventMapBottom<>())
		.add(record);
	}
}

package space_habit_frontier.engine.dtos.events.event_maps;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import space_habit_frontier.engine.dtos.events.VisitRecord;
import space_habit_frontier.engine.interfaces.events.EventMap;

public class MethodVisitMap implements EventMap<VisitRecord> {
	private Map<String, EventMapBottom<VisitRecord>> __methodMap;

	public MethodVisitMap() {
		this.__methodMap = new HashMap<>();
	}

	@Override
	public EventMapBottom<VisitRecord> subMap(String key) {
		return __methodMap.getOrDefault(key, new EventMapBottom<>());
	}

	@Override
	public Stream<VisitRecord> getAll() {
		return __methodMap
			.values()
			.stream()
			.flatMap(EventMapBottom::getAll);
	}

	@Override
	public void add(VisitRecord record) {
		__methodMap.computeIfAbsent(record.method(), k -> new EventMapBottom<>())
			.add(record);
	}
}

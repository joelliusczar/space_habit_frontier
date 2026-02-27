package space_habit_frontier.engine.dtos.events.event_maps;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import space_habit_frontier.engine.dtos.events.VisitRecord;
import space_habit_frontier.engine.interfaces.events.EventMap;

public class UrlVisitMap implements EventMap<VisitRecord> {
	private Map<String, MethodVisitMap> urlVisitMap;

	public UrlVisitMap() {
		this.urlVisitMap = new HashMap<>();
	}

	@Override
	public MethodVisitMap subMap(String key) {
		return urlVisitMap.getOrDefault(key, new MethodVisitMap());
	}

	@Override
	public Stream<VisitRecord> getAll() {
		return urlVisitMap.values()
			.stream()
			.flatMap(MethodVisitMap::getAll);
	}

	@Override
	public void add(VisitRecord record) {
		urlVisitMap
		.computeIfAbsent(record.urlPath(), k -> new MethodVisitMap())
		.add(record);
	}

}

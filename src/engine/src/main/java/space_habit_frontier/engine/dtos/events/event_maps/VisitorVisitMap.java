package space_habit_frontier.engine.dtos.events.event_maps;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import space_habit_frontier.engine.dtos.events.VisitRecord;
import space_habit_frontier.engine.interfaces.events.EventMap;

public class VisitorVisitMap implements EventMap<VisitRecord> {
	private ConcurrentMap<String, UrlVisitMap> visitorMap;
	
	public VisitorVisitMap() {
		this.visitorMap = new ConcurrentHashMap<>();
	}

	@Override
	public void add(VisitRecord record) {
		visitorMap
			.computeIfAbsent(String.valueOf(record.visitorId()),
				k -> new UrlVisitMap())
			.add(record);
	}

	@Override
	public UrlVisitMap subMap(String key) {
		return visitorMap.getOrDefault(key, new UrlVisitMap());
	}

	@Override
	public Stream<VisitRecord> getAll() {
		return visitorMap.values()
			.stream()
			.flatMap(m -> m.getAll());
	}
}

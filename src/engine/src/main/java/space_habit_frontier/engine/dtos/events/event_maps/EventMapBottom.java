package space_habit_frontier.engine.dtos.events.event_maps;

import java.util.Deque;
import java.util.stream.Stream;

import space_habit_frontier.engine.interfaces.events.EventMap;

public class EventMapBottom<T> implements EventMap<T> {
	private Deque<T> records;

	public EventMapBottom() {
		this.records = new java.util.ArrayDeque<>();
	}
	
	@Override
	public EventMapBottom<T> subMap(String key) {
		return this;
	}

	@Override
	public Stream<T> getAll() {
		return records.reversed().stream();
	}

	@Override
	public void add(T record) {
		records.add(record);
	}
}

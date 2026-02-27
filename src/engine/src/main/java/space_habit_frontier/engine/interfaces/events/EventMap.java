package space_habit_frontier.engine.interfaces.events;

import java.util.stream.Stream;

public interface EventMap<T> {
	EventMap<T> subMap(String key);
	Stream<T> getAll();
	void add(T record);
}

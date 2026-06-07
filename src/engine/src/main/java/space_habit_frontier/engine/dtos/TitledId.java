package space_habit_frontier.engine.dtos;

import java.util.UUID;

public class TitledId {
	private UUID __id;
	private String __title = "";

	public TitledId(UUID id, String title) {
		this.__id = id;
		this.__title = title;
	}

	public UUID id() {
		return this.__id;
	}

	public void setId(UUID id) {
		this.__id = id;
	}

	public String title() {
		return this.__title;
	}

	public void setTitle(String title) {
		this.__title = title;
	}

}
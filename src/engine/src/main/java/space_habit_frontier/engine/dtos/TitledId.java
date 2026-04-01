package space_habit_frontier.engine.dtos;

import java.util.UUID;

public class TitledId {
	private UUID id;
	private String title = "";

	public TitledId(UUID id, String title) {
		this.id = id;
		this.title = title;
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
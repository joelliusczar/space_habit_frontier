package space_habit_frontier.engine.dtos.monsters;

import space_habit_frontier.engine.dtos.NamedIntId;

public class MonsterDefinition extends NamedIntId {

	private String exposition = "";
	private int xpMultiplier = 1;

	public MonsterDefinition(long id, String name) {
		super(id, name);
	}

	public String getExposition() {
		return exposition;
	}

	public MonsterDefinition setExposition(String description) {
		this.exposition = description;
		return this;
	}

	public int getXpMultiplier() {
		return xpMultiplier;
	}

	public MonsterDefinition setXpMultiplier(int xpMultiplier) {
		this.xpMultiplier = xpMultiplier;
		return this;
	}

}

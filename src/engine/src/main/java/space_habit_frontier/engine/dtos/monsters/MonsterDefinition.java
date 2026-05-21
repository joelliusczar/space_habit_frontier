package space_habit_frontier.engine.dtos.monsters;

import space_habit_frontier.engine.dtos.NamedIntId;

public class MonsterDefinition extends NamedIntId {

	private String __exposition = "";
	private int __xpMultiplier = 1;

	public MonsterDefinition(long id, String name) {
		super(id, name);
	}

	public String exposition() {
		return __exposition;
	}

	public MonsterDefinition setExposition(String description) {
		this.__exposition = description;
		return this;
	}

	public int xpMultiplier() {
		return __xpMultiplier;
	}

	public MonsterDefinition setXpMultiplier(int xpMultiplier) {
		this.__xpMultiplier = xpMultiplier;
		return this;
	}

}

package space_habit_frontier.engine.dtos.monsters;

public class Monster {

	private MonsterDefinition definition;
	private long currentHp;
	private long lvl;

	public MonsterDefinition getDefinition() {
		return definition;
	}

	public Monster setDefinition(MonsterDefinition definition) {
		this.definition = definition;
		return this;
	}

	public long getCurrentHp() {
		return currentHp;
	}

	public Monster setCurrentHp(long currentHp) {
		this.currentHp = currentHp;
		return this;
	}

	public long getLvl() {
		return lvl;
	}

	public Monster setLvl(long lvl) {
		this.lvl = lvl;
		return this;
	}

}

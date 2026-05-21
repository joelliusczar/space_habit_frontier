package space_habit_frontier.engine.dtos.monsters;

public class Monster {

	private MonsterDefinition __definition;
	private long __currentHp;
	private long __lvl;

	public MonsterDefinition definition() {
		return __definition;
	}

	public Monster setDefinition(MonsterDefinition definition) {
		this.__definition = definition;
		return this;
	}

	public long currentHp() {
		return __currentHp;
	}

	public Monster setCurrentHp(long currentHp) {
		this.__currentHp = currentHp;
		return this;
	}

	public long lvl() {
		return __lvl;
	}

	public Monster setLvl(long lvl) {
		this.__lvl = lvl;
		return this;
	}

}

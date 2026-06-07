package space_habit_frontier.engine.dtos.user_moves;

import space_habit_frontier.engine.dtos.monsters.Monster;


public class UserMoveDto<T> {
	private int __damageDealt;
	private int __damageTaken;
	private int __moneyGained;
	private int __xpGained;
	private Monster __monster;
	private T __entity;

	public T entity() {
		return __entity;
	}

	public UserMoveDto<T> setEntity(T entity) {
		this.__entity = entity;
		return this;
	}

	public int damageDealt() {
		return __damageDealt;
	}

	public UserMoveDto<T> setDamageDealt(int damageDealt) {
		this.__damageDealt = damageDealt;
		return this;
	}

	public int damageTaken() {
		return __damageTaken;
	}

	public UserMoveDto<T> setDamageTaken(int damageTaken) {
		this.__damageTaken = damageTaken;
		return this;
	}

	public int moneyGained() {
		return __moneyGained;
	}

	public UserMoveDto<T> setMoneyGained(int moneyGained) {
		this.__moneyGained = moneyGained;
		return this;
	}

	public int xpGained() {
		return __xpGained;
	}

	public UserMoveDto<T> setXpGained(int xpGained) {
		this.__xpGained = xpGained;
		return this;
	}

	public Monster monster() {
		return __monster;
	}

	public UserMoveDto<T> setMonster(Monster monster) {
		this.__monster = monster;
		return this;
	}

}

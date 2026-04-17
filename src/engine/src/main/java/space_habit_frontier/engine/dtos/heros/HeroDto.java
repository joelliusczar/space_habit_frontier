package space_habit_frontier.engine.dtos.heros;

import java.util.List;
import java.util.UUID;

import space_habit_frontier.engine.constants.BonusTypes;
import space_habit_frontier.engine.constants.PenaltyTypes;
import space_habit_frontier.engine.dtos.monsters.Monster;
import space_habit_frontier.engine.interfaces.dungeons.Dungeon;
import space_habit_frontier.engine.interfaces.items.Armor;
import space_habit_frontier.engine.interfaces.items.Item;
import space_habit_frontier.engine.interfaces.items.Weapon;

public class HeroDto {
	private UUID id;
	private long xp;
	private long gold;
	private List<Item> inventory;
	private Monster monster;
	private Dungeon dungeon;
	private long currenthp;
	private BonusTypes[] activebonustypes;
	private PenaltyTypes[] activepenaltytypes;
	private Weapon weaponright;
	private Weapon weaponleft;
	private Armor frontarmor;
	private Armor backarmor;
	private Armor toparmor;
	private Armor bottomarmor;

	public UUID id() {
		return id;
	}

	public HeroDto setId(UUID value) {
		id = value;
		return this;
	}

	public long xp() {
		return xp;
	}

	public HeroDto setXp(long value) {
		xp = value;
		return this;
	}

	public long lvl() {
		var result = Math.floor((Math.log((xp/100) + 1)/Math.log(1.25)) + 1);
		return (long)result;
	}

	public long gold() {
		return gold;
	}

	public HeroDto setGold(long value) {
		gold = value;
		return this;
	}

	public List<Item> inventory() {
		return inventory;
	}

	public HeroDto setInventory(List<Item> value) {
		inventory = value;
		return this;
	}

	public Monster monster() {
		return monster;
	}

	public HeroDto setMonster(Monster value) {
		monster = value;
		return this;
	}

	public Dungeon dungeon() {
		return dungeon;
	}

	public HeroDto setDungeon(Dungeon value) {
		dungeon = value;
		return this;
	}

	public long currenthp() {
		return currenthp;
	}

	public HeroDto setCurrentHp(long value) {
		currenthp = value;
		return this;
	}

	public BonusTypes[] activeBonusTypes() {
		return activebonustypes;
	}

	public HeroDto setActiveBonusTypes(BonusTypes[] value) {
		activebonustypes = value;
		return this;
	}

	public PenaltyTypes[] activePenaltyTypes() {
		return activepenaltytypes;
	}

	public HeroDto setActivePenaltyTypes(PenaltyTypes[] value) {
		activepenaltytypes = value;
		return this;
	}

	public Weapon weaponRight() {
		return weaponright;
	}

	public HeroDto setWeaponRight(Weapon value) {
		weaponright = value;
		return this;
	}
	
	public Weapon weaponLeft() {
		return weaponleft;
	}

	public HeroDto setWeaponLeft(Weapon value) {
		weaponleft = value;
		return this;
	}

	public Armor frontArmor() {
		return frontarmor;
	}

	public HeroDto setFrontArmor(Armor value) {
		frontarmor = value;
		return this;
	}

	public Armor backArmor() {
		return backarmor;
	}

	public HeroDto setBackArmor(Armor value) {
		backarmor = value;
		return this;
	}

	public Armor topArmor() {
		return toparmor;
	}

	public HeroDto setTopArmor(Armor value) {
		toparmor = value;
		return this;
	}

	public Armor bottomArmor() {
		return bottomarmor;
	}

	public HeroDto setBottomArmor(Armor value) {
		bottomarmor = value;
		return this;
	}
}

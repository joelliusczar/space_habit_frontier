import type { NamedIdItem } from "./generics";

export interface MonsterDefinition extends NamedIdItem {
	exposition: string;
	xpMultiplier: number;
}

export interface Monster {
	definition: MonsterDefinition;
	currentHp: number;
	lvl: number;
}

export interface UserMoveResult<T> {
	 damageDealt: number;
	 damageTaken: number;
	 moneyGained: number;
	 xpGained: number;
	 monster: Monster;
	 entity: T;
}
import type { MonthDay }  from "./dates";
import type { TitledTokenItem } from "./generics";

export interface TodoListItemDto extends TitledTokenItem {};

export const CycleRateTypes = {
	DAILY: 0,
	WEEKLY: 1,
	MONTHLY: 2,
	YEARLY: 3,
	NEVER: 4,
};

export type FormValues = {
	id: string,
	title: string,
	note: string,
	repeattype: number,
	repeatcount: number,
	duedatetimestamp: Date | null,
	yearactivedays: MonthDay[],
	monthactivedays: number[],
	rateinversionflag: boolean,
	weekactivedays: string[],
	repeatrate: number,
	poisonous: boolean,
	risk: number,
	effectivedatetimestamp: Date,
	expirationdatetimestamp: Date | null
};
import type { MonthDay }  from "./dates";

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
	duedatetimestamp: Date | null,
	yearactivedays: MonthDay[],
	yearlySkipMod: boolean,
	monthlyDueDays: number[],
	monthlySkipMod: boolean,
	weekactivedays: string[],
	repeatrate: number,
	poisonous: boolean,
	risk: number,
	effectivedatetimestamp: Date,
	activeToDate: Date | null
};
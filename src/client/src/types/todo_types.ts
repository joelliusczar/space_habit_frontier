import type { MonthDay }  from "./date_types";

export const CycleRateTypes = {
	DAILY: 0,
	WEEKLY: 1,
	MONTHLY: 2,
	YEARLY: 3,
	NEVER: 4,
};

export type FormValues = {
	id: number,
	name: string,
	note: string,
	rateType: number,
	dueDate: string | null,
	yearlyDueDays: MonthDay[],
	yearlySkipMod: boolean,
	monthlyDueDays: number[],
	monthlySkipMod: boolean,
	dueDaysOfWeek: string[],
	dailyRate: number,
	poisonous: boolean,
	danger: number,
	activeFromDate: string,
	activeToDate: string | null
};
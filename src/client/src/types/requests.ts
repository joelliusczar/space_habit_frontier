export const CallStatus = {
	Inert: "",
	Dim: "dim",
	Hide: "hide"
};

export type CallStatusT = typeof CallStatus[keyof typeof CallStatus];

export type ApiResult<T> = {
	data: T | null,
	error: string | null,
	loading: CallStatusT
};

export type RouteId = string | string[];
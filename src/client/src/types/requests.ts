export const CallStatus = {
	Inert: "",
	Dim: "dim",
	Hide: "hide"
};

export type LoadingType = typeof CallStatus[keyof typeof CallStatus];

export type ActiveLoadingType = Exclude<LoadingType, "">;

export type ApiResult<T> = {
	data: T | null,
	error: string | null,
	loading: LoadingType
};

export type RouteId = string | string[];
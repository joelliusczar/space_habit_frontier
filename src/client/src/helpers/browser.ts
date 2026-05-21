import type { StringObject } from "../types/generics";

export const cookieToObject = (cookie: string): StringObject => {
	const kvps = cookie.split(";");
	const obj: StringObject = {};
	for(const pair of kvps) {
		if (!pair) continue;
		const pairSplit = pair.split("=");
		obj[pairSplit[0].trim()] = pairSplit[1].trim();
	}
	return obj;
};

export const checkResponse = async (response: Response) => {
	if (!response.ok) {
		const errorData = await response.json();
		console.error(errorData);
		throw new Error(`Error ${response.status}`);
	}
};
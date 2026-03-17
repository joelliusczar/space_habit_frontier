import type { StringObject } from "../types/generics";
const { fetch: oldFetch } = window;

export const sharedHeaders: StringObject = {};

window.fetch = async (resource, options) => {
	if (!options) options = {};
	if (!options.headers) options.headers = {};
	options.headers = {
		...sharedHeaders,
		...options.headers,
	};
	console.log("options.headers:", options.headers);

	return await oldFetch(resource, options);
};
import type { LookupsDtoT } from "../types/lookups_types";

export const fetchLookups = () => {
	const abortController = new AbortController();
	return {
		abortController: abortController,
		call: async () => {
			const response = await fetch(
				"/api/lookups",
				{ signal: abortController.signal }
			);
			return await response.json() as LookupsDtoT;
		},
	};
};
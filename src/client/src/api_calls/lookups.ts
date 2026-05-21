import { checkResponse } from "../helpers/browser";
import type { LookupsDto } from "../types/lookups";

export const Calls = {
	get: () => {
		const abortController = new AbortController();
		return {
			abortController: abortController,
			call: async () => {
				const response = await fetch(
					"/api/lookups",
					{ signal: abortController.signal }
				);
				await checkResponse(response);
				return await response.json() as LookupsDto;
			},
		};
	},
};


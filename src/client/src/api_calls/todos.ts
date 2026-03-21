import type { FormValues } from "../types/todos";

export const Calls = {
	add: (data: FormValues) => {
		const abortController = new AbortController();
		return {
			abortController: abortController,
			call: async () => {
				const response = await fetch(
					"/api/todos",
					{
						method: "POST",
						body: JSON.stringify(data),
						headers: {
							"Content-Type": "application/json",
						},
						signal: abortController.signal
					}
				);
				return await response.json();
			},
		};
	},
	all: () => {
		const abortController = new AbortController();
		return {
			abortController: abortController,
			call: async () => {
				const response = await fetch(
					"/api/todos/all",
					{
						method: "GET",
						signal: abortController.signal
					}
				);
				return await response.json();
			},
		};
	},
};


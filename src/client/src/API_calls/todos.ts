import type { FormValues } from "../types/todo_types";

export const createTodo = (data: FormValues) => {
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
};
export const createTodo = (data: FormData) => {
	const abortController = new AbortController();
	return {
		abortController: abortController,
		call: async () => {
			const response = await fetch(
				"/api/todos",
				{
					method: "POST",
					body: data,
					signal: abortController.signal
				}
			);
			return await response.json();
		},
	};
};
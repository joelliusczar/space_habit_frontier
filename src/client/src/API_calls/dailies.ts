export const createDaily = (data: FormData) => {
	const abortController = new AbortController();
	return {
		abortController: abortController,
		call: async () => {
			const response = await fetch(
				"/api/dailies",
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
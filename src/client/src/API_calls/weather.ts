


export const fetchWeather = () => {
	const abortController = new AbortController();
	return {
		abortController: abortController,
		call: async () => {
			const response = await fetch(
				"/api/weatherforecast",
				{ signal: abortController.signal }
			);
			return await response.json();
		},
	};
};
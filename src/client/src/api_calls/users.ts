import type { UserCreationInfo } from "../types/users";

export const Calls = {
	signup: (data: UserCreationInfo) => {
			const abortController = new AbortController();
			return {
				abortController: abortController,
				call: async () => {
					const response = await fetch(
						"/api/users/signup",
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
		}
};
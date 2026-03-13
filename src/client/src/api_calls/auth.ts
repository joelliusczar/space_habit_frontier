import type { UserCreationInfo } from "../types/users";

export const Calls = {
	signup: (data: UserCreationInfo) => {
			const abortController = new AbortController();
			return {
				abortController: abortController,
				call: async () => {
					const response = await fetch(
						"/api/auth/signup",
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
	signin: (username: string, password: string) => {
			const abortController = new AbortController();
			return {
				abortController: abortController,
				call: async () => {
					const formData = new window.FormData();
					formData.append("username", username);
					formData.append("password", password);
					const response = await fetch(
						"/api/auth/open-signin",
						{
							method: "POST",
							body: formData,
							signal: abortController.signal
						}
					);
					return await response.json();
				},
			};
	}
};
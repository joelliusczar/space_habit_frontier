import { checkResponse } from "../helpers/browser";
import type { FormValues, TodoListItemDto } from "../types/todos";
import type { Titled } from "../types/generics";
import type { UserMoveResult } from "../types/quest";

export const Calls = {
	get: (id: string) => {
		const abortController = new AbortController();
		return {
			abortController: abortController,
			call: async () => {
				const response = await fetch(
					`/api/todos/${id}`,
					{
						method: "GET",
						signal: abortController.signal
					}
				);
				await checkResponse(response);
				return await response.json() as FormValues;
			},
		};
	},
	add: (data: Titled) => {
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
				await checkResponse(response);
				return await response.json() as TodoListItemDto;
			},
		};
	},
	update: (id: string, data: FormValues) => {
		const abortController = new AbortController();
		return {
			abortController: abortController,
			call: async () => {
				const response = await fetch(
					`/api/todos/${id}`,
					{
						method: "PUT",
						body: JSON.stringify(data),
						headers: {
							"Content-Type": "application/json",
						},
						signal: abortController.signal
					}
				);
				await checkResponse(response);
				return await response.json() as FormValues;
			},
		};
	},
	delete: (id: string) => {
		const abortController = new AbortController();
		return {
			abortController: abortController,
			call: async () => {
				const response = await fetch(
					`/api/todos/${id}`,
					{
						method: "DELETE",
						signal: abortController.signal
					}
				);
				await checkResponse(response);
			},
		};
	},
	complete: (id: string) => {
		const abortController = new AbortController();
		return {
			abortController: abortController,
			call: async () => {
				const response = await fetch(
					`/api/todos/complete/${id}`,
					{
						method: "POST",
						signal: abortController.signal
					}
				);
				await checkResponse(response);
				return await response.json() as UserMoveResult<TodoListItemDto>;
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
				await checkResponse(response);
				return await response.json() as TodoListItemDto[];
			},
		};
	},
};


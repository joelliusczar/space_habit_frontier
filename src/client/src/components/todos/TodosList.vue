<script setup lang="ts">
import { onMounted, ref, computed } from "vue";
import { useRouter } from "vue-router";
import { Calls } from "../../api_calls/todos";
import TodoListItem from "./TodoListItem.vue";
import type { TodoListItemDto } from "../../types/todos";
import { useFormSubmit } from "../../composables/useFormSubmit";
import LoadingBackdrop from "../shared/LoadingBackdrop.vue";
import { useLoader } from "../../composables/useLoader";
import { CallStatus } from "../../types/requests";

const formName = "todo-add";

const router = useRouter();
const title = ref("");

const { data, loading, error, communicate } = useLoader<TodoListItemDto[]>(
		async () => {
	const requestObj = Calls.all();
	return await requestObj.call();
});

communicate();


function openAddNew() {
	router.push({ name: "todoAdd" });
}

const { loading: saving } = useFormSubmit(
		formName,
		async () => {
			try {
				const requestObj = Calls.add({ title: title.value });
				const result = await requestObj.call();
				router.push({ name: "todos" });
			}
			catch (error) {
				console.error("Error adding to-do:", error);
			}
		}
	);

</script>

<template>
	<LoadingBackdrop :loading="loading || saving" :error="error">
		<div>
			<input type="text" v-model="title" />
			<form id="todo-add" action="">
				<button
					class="button"
					type="submit"
				>
					+
				</button>
			</form>
		</div>
		<h2>Active To-dos</h2>
		<div v-for="value in data" :key="value.id">
			<todo-list-item :todo="value"/>
		</div>
	</LoadingBackdrop>
</template>

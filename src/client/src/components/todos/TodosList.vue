<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { Calls } from "../../api_calls/todos";
import TodoListItem from "./TodoListItem.vue";
import type { TodoListItemDto } from "../../types/todos";
import { useFormSubmit } from "../../composables/useFormSubmit";

const formName = "todo-add";

const router = useRouter();
const data = ref<TodoListItemDto[] | null>(null);
const title = ref("");

onMounted(async () => {
	const requestObj = Calls.all();
	const response = await requestObj.call();
	data.value = response;
});

function openAddNew() {
	router.push({ name: "todoAdd" });
}

useFormSubmit(
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
	<div>
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
	</div>
</template>

<script setup lang="ts">
	import { ref } from "vue";
	import type { TodoListItemDto} from "../../types/todos";
	import OptionsButton from "../shared/options_button/OptionsButton.vue";
	import { useLoader } from "../../composables/useLoader";
	import { Calls } from "../../api_calls/todos";
	import type { UserMoveResult } from "../../types/quest";

	const { todo } = defineProps<{
		todo: TodoListItemDto
	}>();

	const isCompleted = ref(false);

	const { 
		data,
		loading,
		error,
		communicate } = useLoader<UserMoveResult<TodoListItemDto>>(
			async () => {
		const requestObj = Calls.complete(todo.id);
		return await requestObj.call();
	});

	const toggleCompleted = async () => {
		const result = await communicate();
		isCompleted.value = !isCompleted.value;
	};

	const options = [
		{
			label: "Edit",
			to: { name: "todoEdit", params: { id: todo.id } }
		},
		{
			label: "Delete",
			onClick: () => {
				console.log("Delete clicked");
			}
		}
	];

</script>

<template>
	<div class="todo-root">
		<div>
			<options-button :options="options"/>
		</div>
		<div>{{ todo.title }}</div>
		<div>
			<input 
				type="checkbox" 
				:checked="isCompleted" 
				@change="toggleCompleted" 
			/>
		</div>
	</div>
</template>

<style scoped>
	.todo-root {
		margin: 1rem;
		display: grid;
		grid-template-columns: 75px 1fr 50px;
	}

	input[type="checkbox"] {
		width: 40px;
		height: 40px;
	}
</style>
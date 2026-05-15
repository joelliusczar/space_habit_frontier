<script setup lang="ts">
	import { ref } from "vue";
	import type { TodoListItemDto} from "../../types/todos";
	import OptionsButton from "../shared/options_button/OptionsButton.vue";

	const { todo } = defineProps<{
		todo: TodoListItemDto
	}>();

	const isCompleted = ref(false);

	const toggleCompleted = () => {
		isCompleted.value = !isCompleted.value;
	};

	const options = [
		{
			label: "Edit",
			href: "/todos/edit/" + todo.id,
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
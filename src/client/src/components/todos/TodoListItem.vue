<script setup lang="ts">
	import { ref } from "vue";
	import type { TodoListItemDto} from "../../types/todos";
	const { todo } = defineProps<{
		todo: TodoListItemDto
	}>();

	const isCompleted = ref(false);

	const toggleCompleted = () => {
		isCompleted.value = !isCompleted.value;
	};

</script>

<template>
	<div class="todo-root">
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
		grid-template-columns: 1fr 50px;
	}

	input[type="checkbox"] {
		width: 40px;
		height: 40px;
	}
</style>
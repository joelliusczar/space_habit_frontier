<script setup lang="ts">
import { useLookups } from "./composables/useLookups";
import TodosList from "./components/todos/TodosList.vue";
import TodoEdit from "./components/todos/TodoEdit.vue";

const routes = {
	"": TodosList
};

useLookups();
</script>

<template>
	<header>
		<div class="wrapper">
		</div>
	</header>

	<main class="grid">
		<TodoEdit />
	</main>
</template>
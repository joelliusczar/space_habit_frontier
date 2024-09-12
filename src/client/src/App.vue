<script setup lang="ts">
import HelloWorld from "./components/HelloWorld.vue";
import DailiesList from "./components/todos/TodosList.vue";
import EditDaily from "./components/todos/EditTodo.vue";
import { useLookups } from "./composables/useLookups";

useLookups();
</script>

<template>
	<header>
		<div class="wrapper">
		</div>
	</header>

	<main class="grid">
		<EditDaily />
		<DailiesList />
	</main>
</template>


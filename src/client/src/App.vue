<script setup lang="ts">
import { ref } from "vue";
import { useLookups } from "./composables/useLookups";
import { RouterView } from "vue-router";


useLookups();
</script>

<template>
	<header>
		<h1>Space Habit Frontier</h1>
		<div class="wrapper">
		</div>
	</header>

	<main class="grid">
		<RouterView />
	</main>
</template>

<style scoped>
	.parent {
		display: grid;
		grid-template-columns: 240px 1fr;
		grid-template-rows: 1fr;
	}

	header {
		padding-inline-start: 25px;
		background-color: #0A5;
		color: #FFF;
		grid-column: 1 / 3
	}
</style>
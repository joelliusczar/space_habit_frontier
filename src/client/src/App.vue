<script setup lang="ts">
import { useLookups } from "./composables/useLookups";
import { RouterView } from "vue-router";
import UserSigninProvider from "./components/users/UserSigninProvider.vue";


useLookups();
</script>

<template>
	<header>
		<h1>Space Habit Frontier</h1>
		<div class="wrapper">
		</div>
	</header>

	<main class="grid">
		<UserSigninProvider>
			<RouterView />
		</UserSigninProvider>
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
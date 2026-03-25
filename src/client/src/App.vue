<script setup lang="ts">
import { RouterView, RouterLink } from "vue-router";
import UserSigninProvider from "./components/users/UserSigninProvider.vue";


</script>

<template>
	<header>
		<h1>Space Habit Frontier</h1>
		<div class="wrapper">
		</div>
	</header>

	<main class="grid">
		<router-link to="/todo/list">Todos</router-link>
		<user-signin-provider>
			<router-view />
		</user-signin-provider>
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
<script setup lang="ts">
import { RouterView } from "vue-router";
import UserSigninProvider from "./components/users/UserSigninProvider.vue";
import NavFooter from "./NavFooter.vue";


</script>

<template>
	<div class="parent">
		<header>
			<h1>Space Habit Frontier</h1>
			<div class="wrapper">
			</div>
		</header>
		<main class="grid">
			<user-signin-provider>
				<router-view />
			</user-signin-provider>
		</main>
		<footer>
			<nav-footer/>
		</footer>
	</div>
</template>

<style scoped>
	.parent {
		display: grid;
		grid-template-rows: 50px 1fr 50px;
		height: 100vh;
	}

	header {
		padding-inline-start: 25px;
		background-color: #0A5;
		color: #FFF;
	}
	
	footer {
		background-color: #0A5;
	}
</style>
<script setup lang="ts">
import { storeToRefs } from "pinia";
import { RouterView } from "vue-router";
import UserSigninProvider from "./components/users/UserSigninProvider.vue";
import NavFooter from "./NavFooter.vue";
import { useCredentialsStore } from "./stores/credentials";

const { credentials } = storeToRefs(useCredentialsStore());
</script>

<template>
	<div class="parent">
		<header>
			<h1>Space Habit Frontier</h1>
			<div class="wrapper">
			</div>
		</header>
		<main class="center">
			<user-signin-provider>
				<router-view />
			</user-signin-provider>
		</main>
		<footer>
			<nav-footer v-if="credentials.isSignedIn"/>
		</footer>
	</div>
</template>

<style scoped>
	.parent {
		display: grid;
		grid-template-rows: 50px 1fr 50px;
		height: 100vh;
	}

	.center {
		width: 50%;
		margin: 0 auto;
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
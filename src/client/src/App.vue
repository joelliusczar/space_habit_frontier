<script setup lang="ts">
import HelloWorld from "./components/HelloWorld.vue";
import DailiesList from "./components/Dailies/DailiesList.vue";
import EditDaily from "./components/Dailies/EditDaily.vue";
import { useLookups } from "./composables/useLookups";

useLookups();
</script>

<template>
	<header>
		<div class="wrapper">
		</div>
	</header>
	
	<main>
		<EditDaily />
		<DailiesList />
		<HelloWorld msg="Fuck yeah!" />
	</main>
</template>

<style scoped>
header {
	line-height: 1.5;
}

.logo {
	display: block;
	margin: 0 auto 2rem;
}

@media (min-width: 1024px) {

	.logo {
		margin: 0 2rem 0 0;
	}



	main {
		background-color: chocolate;
	}
}
</style>

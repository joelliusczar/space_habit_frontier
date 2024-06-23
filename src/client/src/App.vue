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
	
	<main class="grid">
		<EditDaily />
		<DailiesList />
	</main>
</template>


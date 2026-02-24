<script setup lang="ts">
import { ref } from "vue";
import { useLookups } from "./composables/useLookups";
import { RouterView } from "vue-router";


useLookups();
</script>

<template>
	<header>
		<div class="wrapper">
		</div>
	</header>

	<main class="grid">
		<RouterView />
	</main>
</template>
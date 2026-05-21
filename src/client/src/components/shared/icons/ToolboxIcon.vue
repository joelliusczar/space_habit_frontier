<script setup lang="ts">
	import { computed } from "vue";

	const { scale = 1, color = "#000" } = defineProps<{
		scale?: number,
		color?: string,
	}>();

	const size = computed(() => {
		return `${scale}em`;
	});

</script>

<template>
	<svg xmlns="http://www.w3.org/2000/svg" :width="size" :height="size" viewBox="0 0 24 24">
		<path d="M0 0h24v24H0z" fill="none" />
		<path fill="none" :stroke="color" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V5a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2M4 21a2 2 0 0 1-2-2v-7c0-.6.3-1.3.7-1.7l2.6-2.6C5.7 7.3 6.4 7 7 7h10c.6 0 1.3.3 1.7.7l2.6 2.6c.4.4.7 1.2.7 1.7v7a2 2 0 0 1-2 2Zm-2-7h20M9 16v-4m6 4v-4" />
	</svg>
</template>
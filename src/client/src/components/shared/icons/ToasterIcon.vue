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
		<g fill="none" :stroke="color" stroke-linecap="round" stroke-linejoin="round" stroke-width="2">
			<path d="M16 10V5.7A2 2 0 0 0 15 2H9a2 2 0 0 0-1 3.7V10m-2 0h12" />
			<path d="M4 7a2 2 0 0 0-2 2v11a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2" />
			<circle cx="8" cy="16" r="2" />
			<path d="M14 16h4m-2-2v8" />
		</g>
	</svg>
</template>
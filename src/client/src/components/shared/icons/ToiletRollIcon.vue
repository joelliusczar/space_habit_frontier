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
			<ellipse cx="10" cy="8" rx="3" ry="2" />
			<ellipse cx="10" cy="8" rx="7" ry="6" />
			<path d="M3 8v8c0 3.3 3.1 6 7 6s7-2.7 7-6V8c0 2.2 2.2 4 5 4v8c-2.8 0-5-1.8-5-4m-7-2v2m0 4v2" />
		</g>
	</svg>
</template>
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
			<path d="m10 10l4-3m-4 0l4 3" />
			<ellipse cx="12" cy="12" rx="9" ry="10" />
			<path d="m2 22l5-5M18.69 5.31L22 2" />
		</g>
	</svg>
</template>
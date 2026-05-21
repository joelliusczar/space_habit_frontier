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
		<path fill="none" :stroke="color" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.75 4a2 2 0 0 0-3.5 0L2.2 18A2 2.1 0 0 0 4 21h16a2 2 0 0 0 1.75-3ZM7.5 9h9m-11 4h13M3 17h18" />
	</svg>
</template>	
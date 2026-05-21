<script setup lang="ts">

</script>

<template>
	<button class="dsf-button">
		<slot />
	</button>
</template>

<style scoped>
	.dsf-button {
		background-color: #007bff;
		color: white;
		border: none;
		padding: 0.5rem 1rem;
		cursor: pointer;
	}
</style>
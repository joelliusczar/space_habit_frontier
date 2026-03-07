<script setup lang="ts">
import { Teleport } from 'vue';

	defineProps<{
		formName: string,
		open: boolean
	}>();
	const emit = defineEmits(["close"]);

	const handleClose = () => {
		// Handle close logic
		emit("close");
	};

</script>

<template>
	<teleport to="body">
		<div v-if="open">
			<div class="top">
				<slot />
			</div>
			<footer class="bottom">
				<button class="button" @click="handleClose">Close</button>
				<form :id="formName" action="">
					<button
						class="button"
						type="submit"
					>
						Submit
					</button>
				</form>
			</footer>
		</div>
	</teleport>
</template>

<style scoped>
</style>
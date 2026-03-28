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
		<div class="modal" v-if="open">
			<div class="top">
				<slot />
			</div>
			<footer class="bottom">
				<form :id="formName" action="">
					<button
						class="button"
						type="submit"
					>
						Submit
					</button>
					<button class="button" @click="handleClose">Close</button>
				</form>
			</footer>
		</div>
	</teleport>
</template>

<style scoped>
.modal {
	max-width: 20rem;
	position: fixed;
	left: 50%;
}
.button {
	margin-inline-end: 10px;
}
.bottom {
	margin-block-start: 5px;
}
</style>
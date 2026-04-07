<script setup lang="ts">
import { provide, ref } from "vue";
import UserSigninModal from "./UserSigninModal.vue";
import { UserSigninKey } from "../../types/providers";
const open = ref(false);

const openSignin = () => {
	open.value = true;
};

provide(UserSigninKey, {
	openSignin
});

</script>

<template>
	<main class="grid">
		<slot />
		<user-signin-modal :open="open" @close="open = false" />
	</main>
</template>

<style scoped>
	.grid {
		background-color: #f7f7f7;
	}
</style>
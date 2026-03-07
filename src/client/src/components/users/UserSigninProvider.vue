<script setup lang="ts">
import { provide, ref } from "vue";
import UserSigninModal from "./UserSigninModal.vue";
import { UserSigninKey } from "../../types/providers";
const open = ref(false);

const openSignin = () => {
	console.log("Opening sign-in modal");
	open.value = true;
};

provide(UserSigninKey, {
	openSignin
});

</script>

<template>
	<main class="grid">
		<slot />
		<UserSigninModal :open="open" @close="open = false" />
	</main>
</template>

<style scoped>
</style>
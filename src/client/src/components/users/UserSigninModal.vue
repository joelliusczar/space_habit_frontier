<script setup lang="ts">
import { ref } from "vue";
import { useFormSubmit } from "../../composables/useFormSubmit";
import { Calls } from "../../api_calls/auth";
import FormModal from "../shared/FormModal.vue";
import UserSignin from "./UserSignin.vue";
const formName = "user-sign-in"
defineProps<{
		open: boolean
	}>();
	const emit = defineEmits(["close"]);

	const handleClose = () => {
		// Handle close logic
		emit("close");
	};


</script>

<template>
	<FormModal :formName="formName" :open="open" @close="handleClose">
		<UserSignin :formName="formName" />
	</FormModal>
</template>

<style scoped>
</style>
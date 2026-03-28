<script setup lang="ts">
	import { ref } from "vue";
	import { useFormSubmit } from "../../composables/useFormSubmit";
	import { useCredentialsStore } from "@/stores/credentials";

	const props = defineProps<{
		formName: string
	}>();
	const emit = defineEmits(["sign-in"]);


	const { signin } = useCredentialsStore();

	const formValues = ref({
		username: "",
		password: "",
	});

	useFormSubmit(
		props.formName,
		async () => {
			await signin(formValues.value.username, formValues.value.password);
			emit("sign-in");
		}
	);


</script>

<template>
	<div class="top">
		<h1 class="">Sign In</h1>
		<fieldset class="txt-field">
			<legend>Username:</legend>
			<input
				name="username"
				v-model="formValues.username"
				:form="formName"
			/>
		</fieldset>
		<fieldset class="txt-field">
			<legend>Password:</legend>
			<input
				name="password"
				v-model="formValues.password"
				:form="formName"
				type="password"
			/>
		</fieldset> 	
	</div>
</template>

<style scoped>
</style>
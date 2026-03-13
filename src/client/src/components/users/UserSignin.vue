<script setup lang="ts">
	import { reactive } from "vue";
	import { useFormSubmit } from "../../composables/useFormSubmit";
	import { Calls } from "../../api_calls/auth";

	const props = defineProps<{
		formName: string
	}>();

	const formValues = reactive({
		username: "",
		password: "",
	});

	useFormSubmit(
		props.formName,
		async () => {
			const requestObj = Calls.signin(
				formValues.username,
				formValues.password
			);
			await requestObj.call();
		}
	);


</script>

<template>
	<div class="top">
		<h1 class="">Sign Up</h1>
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
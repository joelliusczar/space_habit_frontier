<script setup lang="ts">
import { reactive } from "vue";
import { useFormSubmit } from "../../composables/useFormSubmit";
import type { UserCreationInfo } from "../../types/users";
import { Calls } from "../../api_calls/auth";
const formName = "user-sign-up"

const formValues = reactive<UserCreationInfo & { passwordconfirm: string }>({
	username: "",
	email: "",
	displayname: "",
	password: "",
	passwordconfirm: ""
});

	useFormSubmit(
		formName,
		async () => {
			const requestObj = Calls.signup({
				username: formValues.username,
				email: formValues.email,
				displayname: formValues.displayname,
				password: formValues.password
			});
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
			<legend>Email:</legend>
			<input
				name="email"
				v-model="formValues.email"
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
		<fieldset class="txt-field">
			<legend>Confirm Password:</legend>
			<input
				name="passwordconfirm"
				v-model="formValues.passwordconfirm"
				:form="formName"
				type="password"
			/>
		</fieldset>
		<form :id="formName" action="">
			<button
				class="button"
				type="submit"
			>
				Submit
			</button>
		</form>
	</div>
</template>

<style scoped>
.top {
	display: flex;
	justify-content: center;
	flex-direction: column;
	max-width: fit-content;
}
</style>
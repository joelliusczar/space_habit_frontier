<script setup lang="ts">
import { inject } from "vue";
import DSFButton from "../shared/DSFButton.vue"
import { RouterLink } from "vue-router";
import { UserSigninKey } from "../../types/providers";

const UserSignin = inject(UserSigninKey);

if (!UserSignin) {
	throw new Error("UserSignin context not found");
}

</script>

<template>
	<div>
		<RouterLink to="/user/sign-up">
			<DSFButton>Sign up</DSFButton>
		</RouterLink>
		<DSFButton @click="UserSignin.openSignin">Sign In</DSFButton>
		
	</div>
</template>
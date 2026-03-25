<script setup lang="ts">
import { inject } from "vue";
import DSFButton from "../shared/DSFButton.vue";
import { useCredentialsStore } from "../../stores/credentials";

const { fullSignout } = useCredentialsStore();

import { UserSigninKey } from "../../types/providers";

const UserSignin = inject(UserSigninKey);

if (!UserSignin) {
	throw new Error("UserSignin context not found");
}

</script>

<template>
	<div>
		<DSFButton @click="fullSignout">Sign Out</DSFButton>
		
	</div>
</template>
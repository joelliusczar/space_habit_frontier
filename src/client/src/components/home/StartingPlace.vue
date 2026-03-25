<script setup lang="ts">
import { storeToRefs } from "pinia";
import GuestScreen from "./GuestScreen.vue"
import SignedInUserHome from "./SignedInUserHome.vue";
import { useCredentialsStore } from "../../stores/credentials";

const { credentials } = storeToRefs(useCredentialsStore());

</script>

<template>
	<div>
		<h1>Home</h1>
		<guest-screen  />
		<signed-in-user-home v-if="credentials.isSignedIn" />
	</div>
</template>
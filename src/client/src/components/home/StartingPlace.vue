<script setup lang="ts">
import { storeToRefs } from "pinia";
import GuestScreen from "./GuestScreen.vue"
import SignedInUserHome from "./SignedInUserHome.vue";
import { useCredentialsStore } from "../../stores/credentials";

const { credentials } = storeToRefs(useCredentialsStore());


</script>

<template>
	<div>
		<guest-screen v-if="!credentials.isSignedIn" />
		<signed-in-user-home v-if="credentials.isSignedIn" />
	</div>
</template>
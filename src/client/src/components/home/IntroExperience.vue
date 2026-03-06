<script setup lang="ts">
import DSFButton from "../shared/DSFButton.vue"
import { RouterLink } from "vue-router";

</script>

<template>
	<div>
		<RouterLink to="/user/sign-up">
			<DSFButton>Sign up</DSFButton>
		</RouterLink>
		
	</div>
</template>
<script setup lang="ts">
import  type { LoadingType } from "../../types/requests";
import { CallStatus } from "../../types/requests";
const { loading, error } = defineProps<{
	loading: LoadingType,
	error: object | null
}>();


</script>

<template>
	<div v-if="loading === CallStatus.Dim" class="backdrop">
		Loading...
	</div>
	<div v-show="loading !== CallStatus.Hide">
		<slot />
	</div>
</template>


<style scoped>
.backdrop {
	width: 100%;
	height: 100%;
	position: fixed;
	background-color: gray;
	opacity: 50%;
}
</style>
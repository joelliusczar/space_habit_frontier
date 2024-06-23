<script setup lang="ts">
import { onMounted, ref } from "vue";
import { fetchWeather } from "../../API_calls/weather";

const data = ref(null);

onMounted(async () => {
	const requestObj = fetchWeather();
	data.value = await requestObj.call();
});

</script>

<template>
	<div class="greetings">
		<h1 class="green">Howdy world!</h1>
		<div>
		</div>
	</div>
</template>
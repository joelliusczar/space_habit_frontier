<script setup lang="ts">
import { ref } from "vue";
import { useRouter, RouterView } from "vue-router";

const router = useRouter();
const data = ref(null);

</script>

<template>
	<h1>Quest</h1>
	<router-view />
</template>


<style scoped>

</style>
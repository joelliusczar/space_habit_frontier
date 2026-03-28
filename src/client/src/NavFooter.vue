<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const data = ref(null);



function openSettings() {
	router.push({ name: "settingsNav" });
}

function openTodos() {
	router.push({ name: "todos" });
}

function openActions() {
	router.push({ name: "actions" });
}

function openStore() {
	router.push({ name: "inventory" });
}

</script>

<template>
	<div>
		<div class="toolbar">
			<button @click="openSettings">Settings</button>
			<button @click="openTodos">Todos</button>
			<button @click="openActions">Actions</button>
			<button @click="openStore">Store</button>
		</div>
	</div>
</template>


<style scoped>
	.toolbar {
		display: flex;
		justify-content: space-around;
	}
</style>
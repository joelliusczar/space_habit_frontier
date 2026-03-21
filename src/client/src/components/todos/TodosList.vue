<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { Calls } from "../../api_calls/todos";
import TodoEdit from "./TodoEdit.vue";

const router = useRouter();
const data = ref(null);

onMounted(async () => {
	const requestObj = Calls.all();
	await requestObj.call();
});

function openAddNew() {
	router.push({ name: "todoAdd" });
}

</script>

<template>
	<div>
		<div>
			<input type="text" />
			<button @click="openAddNew">+</button>
		</div>
	</div>
</template>
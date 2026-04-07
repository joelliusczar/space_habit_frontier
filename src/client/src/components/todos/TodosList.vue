<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { Calls } from "../../api_calls/todos";
import TodoListItem from "./TodoListItem.vue";
import type { TodoListItemDto } from "../../types/todos";

const router = useRouter();
const data = ref<TodoListItemDto[] | null>(null);

onMounted(async () => {
	const requestObj = Calls.all();
	const response = await requestObj.call();
	data.value = response;
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
		<div v-for="value in data" :key="value.id">
			<todo-list-item :todo="value"/>
		</div>
	</div>
</template>

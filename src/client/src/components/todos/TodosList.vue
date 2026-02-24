<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import TodoEdit from "./TodoEdit.vue";

const router = useRouter();
const data = ref(null);

// onMounted(async () => {

// });

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
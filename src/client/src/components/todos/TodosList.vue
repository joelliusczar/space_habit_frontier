<script setup lang="ts">
import { onMounted, ref } from "vue";
import TodoEdit from "./TodoEdit.vue";

const data = ref(null);

// onMounted(async () => {

// });

function openAddNew() {
	console.log("Herrlo");
}

</script>

<template>
	<div>
		<div>
			<input type="text" />
			<button @click="openAddNew">+</button>
			<TodoEdit />
		</div>
	</div>
</template>
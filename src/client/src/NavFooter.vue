<script setup lang="ts">
import { ref } from "vue";
import { useRouter,RouterLink } from "vue-router";
import OliveIcon from "./components/shared/icons/OliveIcon.vue";
import PyramidIcon from "./components/shared/icons/PyramidIcon.vue";
import ToiletRollIcon from "./components/shared/icons/ToiletRollIcon.vue";
import ToolboxIcon from "./components/shared/icons/ToolboxIcon.vue";


const router = useRouter();
const data = ref(null);


</script>

<template>
	<div>
		<div class="toolbar">
			<router-link :to="{ name: 'settingsNav' }">
				<ToolboxIcon :scale="2" color="#fff"/>
			</router-link>
			<router-link :to="{ name: 'todos' }">
				<PyramidIcon :scale="2" color="#fff"/>
			</router-link>
			<router-link :to="{ name: 'actions' }">
				<OliveIcon :scale="2" color="#fff"/>
			</router-link>
			<router-link :to="{ name: 'inventory' }">
				<ToiletRollIcon :scale="2" color="#fff"/>
			</router-link>
		</div>
	</div>
</template>


<style scoped>
	.toolbar {
		display: flex;
		justify-content: space-around;
	}
</style>
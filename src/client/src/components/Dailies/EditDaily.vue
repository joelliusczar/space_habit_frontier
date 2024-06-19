<script setup lang="ts">
	import { ref } from "vue";
	import { useLookups } from "../../composables/useLookups";
	import { useFormSubmit } from "../../composables/useFormSubmit";
	import { createDaily } from "../../API_calls/dailies";

	const showAdvanced = ref(false);

	const { data: lookups } = useLookups();

	const toggleAdvancedShow = () => {
		showAdvanced.value = !showAdvanced.value;
	};

	const cycleRateTypes = lookups?.cycleRateTypes || [];

	useFormSubmit(
		"daily-edit",
		async (form: HTMLFormElement) => {
			const requestObj = createDaily(new FormData(form));
			await requestObj.call();
		}
	);


</script>

<template>
	<div>
		<h1>Edit Daily</h1>
		<div>{{ lookups }}</div>
		<form id="daily-edit" action="">
			<input name="name" />
			<input name="note" />
			<div>
				<button 
					type="button" 
					class="expander"
					@click="toggleAdvancedShow"
				>Extra </button>
			</div>
			<div 
				class="advanced-fields"
				:class="{ 'closed': !showAdvanced, 'open': showAdvanced }"
			>
			<div>
				<label for="rateType">How Often?</label>
				<select name="rateType">
					<option 
						v-for="option of cycleRateTypes" 
						:key="`rate-${option.id}`"
						:value="option.id"
					>
						{{option.name}}
					</option>
				</select>
			</div>
			<div>
				<label for="rate"></label>
				<input name="rate" />
			</div>
				<input name="difficulty" />
				<input name="urgency" />
				<input name="startTime" />
				<input name="activeFromDate" />
				<input name="activeToDate" />
			</div>
			<button type="submit">Submit</button>
		</form>
	</div>
</template>

<style scoped>
.advanced-fields.open {
	display: grid;
}

.closed {
	display: none;
}

.expander {
	min-height: 20px;;
}
</style>
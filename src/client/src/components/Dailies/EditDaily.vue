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
	<div class="">
		<h1 class="text-4xl">Edit Daily</h1>
		<form id="daily-edit" action="">
			<div class="required-block">
				<fieldset>
					<legend>Name:</legend>
					<input name="name" />
				</fieldset>
				<fieldset>
					<legend for="note">Description:</legend>
					<input name="note" />
				</fieldset>
			</div>
			<details>
				<summary>Extra</summary>
				<div>
					<fieldset>
						<legend for="rateType">How Often?</legend>
						<select name="rateType">
							<option 
								v-for="option of cycleRateTypes" 
								:key="`rate-${option.id}`"
								:value="option.id"
							>
								{{option.name}}
							</option>
						</select>
					</fieldset>
					<fieldset>
						<legend for="rate"></legend>
						<input name="rate" />
					</fieldset>
					<fieldset>
						<legend for="difficulty">Difficulty</legend>
						<input name="difficulty" type="range" min="0" max="10">
					</fieldset>
					<fieldset>
						<legend for="urgency">Urgency</legend>
						<input name="urgency" type="range" min="0" max="10">
					</fieldset>
					<fieldset>
						<legend for="startTime">Start Time</legend>
						<input name="startTime" type="time"/>
					</fieldset>
					<fieldset>
						<legend for="activeFromDate">Active Since</legend>
						<input name="activeFromDate" type="date"/>
					</fieldset>
					<fieldset>
						<legend for="activeToDate">Active till</legend>
						<input name="activeToDate" type="date"/>
					</fieldset>
				</div>
			</details>
			<button 
				class="bg-sky-600 px-3 py-1 text-gray-100 shadow-lg hover:shadow-slate-500" 
				type="submit"
			>
				Submit
			</button>
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
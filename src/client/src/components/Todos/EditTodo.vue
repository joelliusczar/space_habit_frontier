<script setup lang="ts">
	import { reactive, watch, computed } from "vue";
	import { useFormSubmit } from "../../composables/useFormSubmit";
	import { createTodo } from "../../API_calls/todos";
	import { CycleRateTypes } from "../../types/todo_types";
	import type { FormValues } from "../../types/todo_types";
	import { monthNames } from "../../types/date_types";
	import CheckmarkIcon from "../shared/icons/CheckmarkIcon.vue";
	import {
		Listbox,
		ListboxButton,
		ListboxOptions,
		ListboxOption
	} from "@headlessui/vue"
	import { formatOrdinal } from "../../helpers/numbers";
	import { monthDays } from "../../helpers/dates";
	import dayjs from "dayjs"

	const formName = "todo-edit"

	const formValues = reactive<FormValues>({
		id: 0,
		name: "",
		note: "",
		rateType: CycleRateTypes.NEVER,
		dueDate: null,
		yearlyDueDays: [],
		yearlySkipMod: false,
		monthlyDueDays: [],
		monthlySkipMod: false,
		dueDaysOfWeek: [
			"monday",
			"tuesday",
			"wednesday",
			"thursday",
			"friday",
			"saturday",
			"sunday",
		],
		dailyRate: 1,
		poisonous: true,
		danger: 5,
		activeFromDate: dayjs().format("YYYY-MM-DD"),
		activeToDate: null
	});

	const monthlySelectLabel = computed(() => {
		if (!formValues.monthlyDueDays.length) return "Open";
		if (formValues.monthlyDueDays.length === 1) {
			return `${formatOrdinal(formValues.monthlyDueDays[0])} of the month`;
		}
		const sorted = [...formValues.monthlyDueDays];
		sorted.sort();
		const last = formatOrdinal(sorted.splice(-1, 1)[0]);
		const joined = sorted.map(n => formatOrdinal(n)).join(", ");
		return `${joined}, and ${last} of the month`;
	});

	const addYearlyDueDate = () => {
		formValues.yearlyDueDays.push({
			month: "January",
			day: 1,
		});
	};

	const removeYearlyDueDate = (_: Event, idx: number) => {
		formValues.yearlyDueDays.splice(idx, 1);
	};

	useFormSubmit(
		formName,
		async () => {
			console.log("submit");
			console.log(formValues);
			const requestObj = createTodo(formValues);
			await requestObj.call();
		}
	);


	watch(formValues, (value) => {
		console.log(value);
	});
</script>

<template>
	<div class="top">
		<div class="column">
			<h1 class="">Edit Todo</h1>
			<div class="required-block">
				<fieldset class="txt-field">
					<legend>Name:</legend>
					<input
						name="name"
						v-model="formValues.name"
						:form="formName"
					/>
				</fieldset>
				<fieldset>
					<legend for="note">Description:</legend>
					<textarea
						name="note"
						v-model="formValues.note"
						:form="formName"
					></textarea>
				</fieldset>
				<fieldset v-show="formValues.rateType === CycleRateTypes.NEVER">
					<legend for="dueDate">Date</legend>
					<input
						name="dueDate"
						type="datetime-local"
						v-model="formValues.dueDate"
					/>
				</fieldset>
				<fieldset>
					<legend for="rateType">Repeats</legend>
					<select
						name="rateType"
						v-model="formValues.rateType"
						:form="formName"
					>
						<option
							v-for="[key, value] of Object.entries(CycleRateTypes)"
							:key="`rate-${key}`"
							:value="value"
							:selected="value === CycleRateTypes.NEVER"
						>
							{{key}}
						</option>
					</select>
				</fieldset>
				<fieldset v-show="formValues.rateType === CycleRateTypes.WEEKLY">
					<legend >Days of the Week</legend>
					<input
						name="monday"
						value="monday"
						type="checkbox"
						v-model="formValues.dueDaysOfWeek"
					/>
					<label for="monday" >Monday</label>
					<input
						name="tuesday"
						value="tuesday"
						type="checkbox"
						v-model="formValues.dueDaysOfWeek"
					/>
					<label for="tuesday" >Tuesday</label>
					<input
						name="wednesday"
						value="wednesday"
						type="checkbox"
						v-model="formValues.dueDaysOfWeek"
					/>
					<label for="wednesday" >Wednesday</label>
					<input
						name="thursday"
						value="thursday"
						type="checkbox"
						v-model="formValues.dueDaysOfWeek"
					/>
					<label for="hursday" >Thursday</label>
					<input
						name="friday"
						value="friday"
						type="checkbox"
						v-model="formValues.dueDaysOfWeek"
					/>
					<label for="friday" >Friday</label>
					<input
						name="saturday"
						value="saturday"
						type="checkbox"
						v-model="formValues.dueDaysOfWeek"
					/>
					<label for="saturday" >Saturday</label>
					<input
						name="sunday"
						value="sunday"
						type="checkbox"
						v-model="formValues.dueDaysOfWeek"
					/>
					<label for="sunday" >Sunday</label>
				</fieldset>
				<div>
					<fieldset v-show="formValues.rateType === CycleRateTypes.MONTHLY" >
							<legend for="monthlyDueDays">Due every...</legend>
							<Listbox
								multiple
								v-model="formValues.monthlyDueDays"
								class="select-parent"
								as="div"
							>
								<ListboxButton
									class="button"
								>
									{{monthlySelectLabel}}
								</ListboxButton>
								<ListboxOptions class="select-option-box">
									<ListboxOption
										v-for="n in 31"
										:key="n"
										:value="n"
										class="select-option"
										v-slot="{ selected }"
								>
									<CheckmarkIcon v-if="selected"/>
									<span>{{formatOrdinal(n)}} of the month</span>
								</ListboxOption>
								</ListboxOptions>
							</Listbox>
					</fieldset>
				</div>
				<div>
					<fieldset v-show="formValues.rateType === CycleRateTypes.YEARLY">
						<legend for="yearlyDueDays">Due every...</legend>
						<button
							@click="addYearlyDueDate"
							class="button add-button"
						>
							+
						</button>
						<div
							v-for="(item, idx) in formValues.yearlyDueDays"
							:key="idx"
						>
							<button
								@click="removeYearlyDueDate($event, idx)"
								class="button"
							>
								-
							</button>
							<Listbox
								v-model="formValues.yearlyDueDays[idx].month"
								class="select-parent"
								as="span"
							>
								<ListboxButton
									class="button yearly-fields"
								>
									{{formValues.yearlyDueDays[idx].month}}
								</ListboxButton>
								<ListboxOptions class="select-option-box">
									<ListboxOption
										v-for="month in monthNames"
										:key="month"
										:value="month"
										class="select-option"
									>
										{{month}}
									</ListboxOption>
								</ListboxOptions>
							</Listbox>
							<Listbox
								v-model="formValues.yearlyDueDays[idx].day"
								class="select-parent"
								as="span"
							>
								<ListboxButton
									class="button"
								>
									{{formatOrdinal(formValues.yearlyDueDays[idx].day)}}
								</ListboxButton>
								<ListboxOptions class="select-option-box yearly-fields">
									<ListboxOption
										v-for="n in monthDays[formValues.yearlyDueDays[idx].month]"
										:key="n"
										:value="n"
										class="select-option"
									>
										{{formatOrdinal(n)}}
									</ListboxOption>
								</ListboxOptions>
							</Listbox>
						</div>
					</fieldset>
				</div>
				<fieldset v-show="formValues.rateType == CycleRateTypes.DAILY">
						<legend for="dailyRate">Rate</legend>
						<span> Every </span>
						<input
							name="dailyRate"
							type="number"
							v-model="formValues.dailyRate"
						/>
						<span> days</span>
				</fieldset>
			</div>
			<details>
				<summary class="summary-button">Extra</summary>
				<div>
					<fieldset>
						<legend for="danger">Danger</legend>
						<input
							name="danger"
							type="range"
							min="0" max="10"
							v-model="formValues.danger"
						>
					</fieldset>
					<fieldset>
						<legend for="startTime">Start Time</legend>
						<input name="startTime" type="time"/>
					</fieldset>
					<fieldset>
						<legend for="activeFromDate">Active Since</legend>
						<input
							name="activeFromDate"
							type="date"
							v-model="formValues.activeFromDate"
						/>
					</fieldset>
					<fieldset>
						<legend for="activeToDate">Active till</legend>
						<input
							name="activeToDate"
							type="date"
							v-model="formValues.activeToDate"
						/>
					</fieldset>
				</div>
			</details>
			<form id="todo-edit" action="">
				<button
					class="button"
					type="submit"
				>
					Submit
				</button>
			</form>
		</div>
	</div>
</template>

<style scoped>

.top {
	display: flex;
	justify-content: center;
}

.column {
	flex-basis: 500px;
}

.summary-button {
	margin-block: 1em;
	cursor:pointer;
	max-width: 5em;
}

.summary-button:hover {
	background-color: #bfbfbf;
}

fieldset {
	border-width: 1px;
	border-radius: 3px;
}


.expander {
	min-height: 20px;;
}

.txt-field>input {
	border-style: solid;
	border-width: 1px;
	border-color: black;
}


.select-option-box {
	background-color: white;
	box-shadow: 0 0 10px darkslategray;
	max-width: 320px;
	max-height: 150px;
	position: absolute;
	padding: 10px;
	border-radius: 1%;
	overflow-y: auto;
	top: -75%;
	z-index: 1;
}

:deep(.select-parent) {
	position: relative;
}

.add-button {
	margin: 10px auto;
}

.yearly-fields {
	margin: 5px;
}

.select-open-button {
	border-width: 0;
	padding: 5px 10px;
}

.select-option {
	list-style:none;
}


.select-option:hover {
	background-color:cornflowerblue;
}
</style>
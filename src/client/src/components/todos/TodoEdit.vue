<script setup lang="ts">
	import { reactive, computed } from "vue";
	import { useRouter } from "vue-router";
	import { useFormSubmit } from "../../composables/useFormSubmit";
	import { Calls } from "../../api_calls/todos";
	import { CycleRateTypes } from "../../types/todos";
	import type { FormValues } from "../../types/todos";
	import { monthNames } from "../../types/dates";
	import CheckmarkIcon from "../shared/icons/CheckmarkIcon.vue";
	import {
		Listbox,
		ListboxButton,
		ListboxOptions,
		ListboxOption
	} from "@headlessui/vue"
	import { formatOrdinal } from "../../helpers/numbers";
	import { monthDays } from "../../helpers/dates";
import router from "@/router";
	

	const formName = "todo-edit"

	const formValues = reactive<FormValues>({
		id: "00000000-0000-0000-0000-000000000000",
		title: "",
		note: "",
		repeattype: CycleRateTypes.NEVER,
		repeatcount: 1,
		duedatetimestamp: null,
		yearactivedays: [],
		rateinversionflag: false,
		monthactivedays: [],
		weekactivedays: [
			"monday",
			"tuesday",
			"wednesday",
			"thursday",
			"friday",
			"saturday",
			"sunday",
		],
		repeatrate: 1,
		poisonous: true,
		risk: 5,
		effectivedatetimestamp: new Date(),
		expirationdatetimestamp: null
	});



	const monthlySelectLabel = computed(() => {
		if (!formValues.monthactivedays.length) return "Open";
		if (formValues.monthactivedays.length === 1) {
			return `${formatOrdinal(formValues.monthactivedays[0])} of the month`;
		}
		const sorted = [...formValues.monthactivedays];
		sorted.sort();
		const last = formatOrdinal(sorted.splice(-1, 1)[0]);
		const joined = sorted.map(n => formatOrdinal(n)).join(", ");
		return `${joined}, and ${last} of the month`;
	});

	const shouldShowSkip = computed(() => {
		return formValues.repeattype === CycleRateTypes.MONTHLY
			|| formValues.repeattype === CycleRateTypes.YEARLY;
	});

	const dueLabel = computed(() => shouldShowSkip && formValues.rateinversionflag
		? "Skip every..." 
		: "Due every...")

	const repeatCountMessage = computed(() => {
		if (! formValues.repeatcount) {
			return "Repeats forever";
		}
		const repeatcount = formValues.repeatcount * 1;
		if (repeatcount < 0) {
			return "Repeats forever";
		}
		if (repeatcount === 1) {
			return "Ends after 1 occurance.";
		}
		return `Ends after ${repeatcount} occurances.`;
	});

	const addYearlyDueDate = () => {
		formValues.yearactivedays.push({
			month: "January",
			day: 1,
		});
	};

	const removeYearlyDueDate = (_: Event, idx: number) => {
		formValues.yearactivedays.splice(idx, 1);
	};

	useFormSubmit(
		formName,
		async () => {
			const requestObj = Calls.add(formValues);
			await requestObj.call();
			router.push({ name: "todos" });
		}
	);

</script>

<template>
	<div class="top">
		<div class="column">
			<h1 class="">Edit Todo</h1>
			<div class="required-block">
				<fieldset class="txt-field">
					<legend>Name:</legend>
					<input
						name="title"
						v-model="formValues.title"
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
				<fieldset v-show="formValues.repeattype === CycleRateTypes.NEVER">
					<legend for="duedatetimestamp">Date</legend>
					<input
						name="duedatetimestamp"
						type="datetime-local"
						v-model="formValues.duedatetimestamp"
					/>
				</fieldset>
				<fieldset>
					<legend for="repeattype">Repeats</legend>
					<select
						name="repeattype"
						v-model="formValues.repeattype"
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
				<fieldset v-show="shouldShowSkip">
						<legend for="rateinversionflag">Skip...</legend>
						<input
							name="rateinversionflag"
							type="checkbox"
							v-model="formValues.rateinversionflag"
						/>
					</fieldset>
				<fieldset v-show="formValues.repeattype === CycleRateTypes.WEEKLY">
					<legend >Days of the Week</legend>
					<input
						name="monday"
						value="monday"
						type="checkbox"
						v-model="formValues.weekactivedays"
					/>
					<label for="monday" >Monday</label>
					<input
						name="tuesday"
						value="tuesday"
						type="checkbox"
						v-model="formValues.weekactivedays"
					/>
					<label for="tuesday" >Tuesday</label>
					<input
						name="wednesday"
						value="wednesday"
						type="checkbox"
						v-model="formValues.weekactivedays"
					/>
					<label for="wednesday" >Wednesday</label>
					<input
						name="thursday"
						value="thursday"
						type="checkbox"
						v-model="formValues.weekactivedays"
					/>
					<label for="hursday" >Thursday</label>
					<input
						name="friday"
						value="friday"
						type="checkbox"
						v-model="formValues.weekactivedays"
					/>
					<label for="friday" >Friday</label>
					<input
						name="saturday"
						value="saturday"
						type="checkbox"
						v-model="formValues.weekactivedays"
					/>
					<label for="saturday" >Saturday</label>
					<input
						name="sunday"
						value="sunday"
						type="checkbox"
						v-model="formValues.weekactivedays"
					/>
					<label for="sunday" >Sunday</label>
				</fieldset>
				<div>
					<fieldset v-show="formValues.repeattype === CycleRateTypes.MONTHLY" >
							<legend for="monthactivedays">{{dueLabel}}</legend>
							<Listbox
								multiple
								v-model="formValues.monthactivedays"
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
					<fieldset v-show="formValues.repeattype === CycleRateTypes.YEARLY">
						<legend for="yearlyDueDays">{{dueLabel}}</legend>
						<button
							@click="addYearlyDueDate"
							class="button add-button"
						>
							+
						</button>
						<div
							v-for="(item, idx) in formValues.yearactivedays"
							:key="idx"
						>
							<button
								@click="removeYearlyDueDate($event, idx)"
								class="button"
							>
								-
							</button>
							<Listbox
								v-model="formValues.yearactivedays[idx].month"
								class="select-parent"
								as="span"
							>
								<ListboxButton
									class="button yearly-fields"
								>
									{{formValues.yearactivedays[idx].month}}
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
								v-model="formValues.yearactivedays[idx].day"
								class="select-parent"
								as="span"
							>
								<ListboxButton
									class="button"
								>
									{{formatOrdinal(formValues.yearactivedays[idx].day)}}
								</ListboxButton>
								<ListboxOptions class="select-option-box yearly-fields">
									<ListboxOption
										v-for="n in monthDays[formValues.yearactivedays[idx].month]"
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
				<fieldset v-show="formValues.repeattype == CycleRateTypes.DAILY">
						<legend for="repeatrate">Rate</legend>
						<span> Every </span>
						<input
							name="repeatrate"
							type="number"
							v-model="formValues.repeatrate"
						/>
						<span> days</span>
				</fieldset>
			</div>
			<details>
				<summary class="summary-button">Extra</summary>
				<div>
					<fieldset>
						<legend for="risk">Danger</legend>
						<input
							name="risk"
							type="range"
							min="0" max="10"
							v-model="formValues.risk"
						>
					</fieldset>
					<fieldset>
						<legend for="startTime">Start Time</legend>
						<input name="startTime" type="time"/>
					</fieldset>
					<fieldset>
						<legend for="effectivedatetimestamp">Active Since</legend>
						<input
							name="effectivedatetimestamp"
							type="datetime-local"
							v-model="formValues.effectivedatetimestamp"
						/>
					</fieldset>
					<fieldset>
						<legend for="expirationdatetimestamp">Active till</legend>
						<input
							name="expirationdatetimestamp"
							type="datetime-local"
							v-model="formValues.expirationdatetimestamp"
						/>
					</fieldset>
					<fieldset class="txt-field" v-if="formValues.repeattype !== CycleRateTypes.NEVER">
						<legend for="repeatcount">{{repeatCountMessage}}</legend>
						<input
							name="repeatcount"
							type="numeric"
							v-model="formValues.repeatcount"
							:form="formName"
						/>
					</fieldset>
					<fieldset>
						<legend for="poisonous">Poisonous</legend>
						<input
							name="poisonous"
							type="checkbox"
							v-model="formValues.poisonous"
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
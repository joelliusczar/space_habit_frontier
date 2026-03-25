import { defineStore, storeToRefs } from "pinia";
import { ref, watch } from "vue";
import { Calls } from "../api_calls/lookups";
import { CallStatus, type ApiResult } from "../types/requests";
import type { LookupsDto } from "../types/lookups";
import { formatError } from "../helpers/errors";
import { useCredentialsStore } from "./credentials";

export const useLookupsStore = defineStore("lookups", () => {
	const fetchResults = ref<ApiResult<LookupsDto>>({ 
		loading: CallStatus.Inert,
		data: null,
		error: null
	});

	const { credentials } = storeToRefs(useCredentialsStore());

	watch(credentials, async (credentials) => {
		if (credentials.isSignedIn) {
			try {
				const requestObj = Calls.get();
				fetchResults.value.data = await requestObj.call();
			}
			catch (err) {
				fetchResults.value.error = formatError(err);
			}
		}
		else {
			fetchResults.value.loading = CallStatus.Inert;
			fetchResults.value.data = null;
			fetchResults.value.error = null;
		}
	}, {immediate: true});

	return { fetchResults };
});
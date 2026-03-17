import { ref, watch } from "vue";
import { Calls } from "../api_calls/lookups";
import { CallStatus, type ApiResult } from "../types/requests";
import type { LookupsDto } from "../types/lookups";
import { formatError } from "../helpers/errors";
import { useCredentials } from "./useSignin";

const fetchResults = ref<ApiResult<LookupsDto>>({ 
	loading: CallStatus.Inert,
	data: null,
	error: null
});

const credentials = useCredentials();

watch(() => credentials.value.isSignedIn, async (isSignedIn) => {
	if (isSignedIn) {
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
});


export const useLookups = () => {

	return fetchResults.value;
};
import { reactive } from "vue";
import { fetchLookups } from "../API_calls/lookups";
import { CallStatus, type ApiResult } from "../types/request_types";
import type { LookupsDtoT } from "../types/lookups_types";
import { formatError } from "../helpers/errors"

const fetchResults = reactive<ApiResult<LookupsDtoT>>({ 
	loading: CallStatus.Inert,
	data: null,
	error: null
});

try {
	const requestObj = fetchLookups();
	fetchResults.data = await requestObj.call();
}
catch (err) {
	fetchResults.error = formatError(err);
}

export const useLookups = () => {

	return fetchResults;
};


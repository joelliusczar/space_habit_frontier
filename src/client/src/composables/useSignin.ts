import { ref, computed } from "vue";
import { Calls } from "../api_calls/auth";

const credentials = ref({
	username: "",
});


export const useSignin = () => {

	return [async(username: string, password: string) => {
		const requestObj = Calls.signin(username, password);
		await requestObj.call();
		credentials.value.username = username;
	}];
};

export const useCredentials = () => {
	const computedCredentials = computed(() => ({
		username: credentials.value.username,
		isSignedIn: !!credentials.value.username,
	}));
	return computedCredentials;
};


// export const useSigninPrompt = () => {

// 	return fetchResults;
// };
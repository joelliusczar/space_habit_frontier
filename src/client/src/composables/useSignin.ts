import { ref, computed } from "vue";
import { Calls } from "../api_calls/auth";
import { cookieToObject } from "../helpers/browser";

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
	
	const computedCredentials = computed(() => {
		const cookieObj = cookieToObject(document.cookie);
		const cookieUsername = decodeURIComponent(cookieObj["username"] || "");
		const username = credentials.value.username || cookieUsername;
		return {
			username: username,
			isSignedIn: !!username,
		};
	});

	return computedCredentials;
};


// export const useSigninPrompt = () => {

// 	return fetchResults;
// };
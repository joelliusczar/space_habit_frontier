import { defineStore } from "pinia";
import { ref, computed } from "vue";
import { cookieToObject } from "../helpers/browser";
import { Calls } from "../api_calls/auth";


export const useCredentialsStore = defineStore("credentials", () => {
	const __credentials = ref({
		username: "",
	});

	const credentials = computed(() => {
		const cookieObj = cookieToObject(document.cookie);
		const cookieUsername = decodeURIComponent(cookieObj["username"] || "");
		const username = __credentials.value.username || cookieUsername;
		return {
			username: username,
			isSignedIn: !!username,
		};
	});

	const signin =  async (username: string, password: string) => {
		const requestObj = Calls.signin(username, password);
		await requestObj.call();
		credentials.value.username = username;
	};

	const fullSignout = async () => {
		const requestObj = Calls.signout();
		await requestObj.call();
		credentials.value.username = "";
	};

	return { __credentials, credentials, signin, fullSignout };
});
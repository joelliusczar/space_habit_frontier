import { defineStore } from "pinia";
import { ref } from "vue";

export const useLoadingStore = defineStore("loading", () => {
	const loading = ref<string>("");
	const error = ref<object | null>(null);

	const setLoading = (loadingType: string) => {
		loading.value = loadingType;
	};

	const setError = (errorMessage: object | null) => {
		error.value = errorMessage;
	};

	return { loading, error, setLoading, setError };
});
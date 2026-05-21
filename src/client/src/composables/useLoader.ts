import { ref } from "vue";
import type { LoadingType } from "../types/requests";
import { CallStatus } from "../types/requests";
import { useSnackbar } from "vue3-snackbar";

export const useLoader = <T, E = object>(
	action: () => Promise<T>,
	loadingValue: LoadingType = CallStatus.Hide
) => {
		const loading = ref(CallStatus.Inert);
		const error = ref<E | null>(null);
		const data = ref<T | null>(null);
		const snackbar = useSnackbar();

		const communicate = async () => {
			loading.value = loadingValue;
			try {
				const result = await action();
				data.value = result;
				snackbar.add({
						type: "success",
						text: "Action successful"
				});
				return result;
			} catch (err) {
				console.error(err);
				error.value = err as E;
				snackbar.add({
						type: "error",
						text: "error"
				});
			} finally {
				loading.value = CallStatus.Inert;
			}
		};

		return { communicate, loading, error, data };
};
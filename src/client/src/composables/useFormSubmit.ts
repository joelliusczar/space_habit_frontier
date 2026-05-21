import { onMounted, onUnmounted, ref } from "vue"
import { CallStatus } from "../types/requests";

export const useFormSubmit = <E = object>(
	id: string,
	sendAction: (params: HTMLFormElement) => Promise<void>
) => {

	const loading = ref(CallStatus.Inert);
	const error = ref<E | null>(null);

	const submit = (e: Event) => {
		e.preventDefault();
		loading.value = CallStatus.Dim;
		sendAction(e.target as HTMLFormElement).catch((err) => {
			error.value = err;
		})
		.finally(() => {
			loading.value = CallStatus.Inert;
		});
	};


	onMounted(() => {
		document.querySelector(`form#${id}`)?.addEventListener("submit", submit);
	});

	onUnmounted(() => {
		document.querySelector(`form#${id}`)?.removeEventListener("submit", submit)
	});

	return { loading, error };
};

interface HTMLElementWithClickHandler extends HTMLElement {
	__clickHandler__?: (event: MouseEvent) => void;
}

export default {
	mounted(el: HTMLElement, binding: { value: (event: MouseEvent) => void }) {
		const clickEl = el as HTMLElementWithClickHandler;
		clickEl.__clickHandler__ = (event: MouseEvent) => {
			if (!(el === event.target || el.contains(event.target as Node))) {
				binding.value(event);
			}
		}
		document.addEventListener("click", clickEl.__clickHandler__);
	},
	unmounted(el: HTMLElement) {
		const clickEl = el as HTMLElementWithClickHandler;
		if (clickEl.__clickHandler__) {
			document.removeEventListener("click", clickEl.__clickHandler__);
			delete clickEl.__clickHandler__;
		}
	}

};
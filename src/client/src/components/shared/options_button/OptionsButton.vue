<script setup lang="ts">
	import { ref, computed, useTemplateRef } from "vue";
	import type { OptionBase } from "./options_button_types";
	import vClickOutside from "../../../directives/click_outside";

	const { options, selectionType = "single" } = defineProps<{
		options?: OptionBase[] | OptionBase,
		selectionType?: string
	}>();

	const menuOpen = ref(false);
	const selectedOption = ref<OptionBase | null>(null);
	const menuOpener = useTemplateRef<HTMLButtonElement | null>("menu-opener");

	const activeOption = computed(() => {
		if (Array.isArray(options)) {
			if (options.length) {
				if (selectedOption.value) {
					return options.find(option => option === selectedOption.value);
				}
				return options[0];
			}
			return null;
		}
		return options;
	});

	const toggleMenu = () => {
		menuOpen.value = !menuOpen.value;
	};

	const closeMenu = (e: Event) => {
		if (menuOpener.value?.contains(e.target as Node)) {
			return;
		}
		menuOpen.value = false;
	};

	const handleOptionClick = (e:Event, option: OptionBase) => {
		if (selectionType === "activate" || selectionType === "both") {
			selectedOption.value = option;
		}
		if (selectionType === "click" || selectionType === "both") {
			if (option.onClick) {
				option.onClick(e);
			}
		}
		menuOpen.value = false;
	};


</script>

<template>
	<span>
		<button 
			v-if="!(activeOption?.href)"
			class="option-btn option-btn-left" 
			@click="handleOptionClick($event, activeOption!)"
			:href="activeOption?.href"
		>
			{{ activeOption?.label || "Options" }}
		</button>
		<a 
			v-else
			class="option-btn option-btn-left" 
			:href="activeOption?.href"
		>
			{{ activeOption?.label || "Options" }}
		</a>
		<button 
			class="option-btn option-btn-right" 
			@click="toggleMenu"
			ref="menu-opener"
		>
			<svg
				width="20"
				height="20"
				viewBox="0 0 24 24"
				fill="none"
				stroke="currentColor"
				stroke-width="2"
				stroke-linecap="round"
				stroke-linejoin="round"
			>
				<path d="M6 9l6 6 6-6"/>
			</svg>
		</button>
		<ul
			id="btn-menu-id"
			v-if="menuOpen && Array.isArray(options) && options.length > 1" 
			class="btn-menu"
			v-click-outside="closeMenu"
		>
			<li 
				v-for="(option, index) in options" 
				:key="index"
				@click="handleOptionClick($event, option)	"
			>
				{{ option.label }}
			</li>
		</ul>
	</span>
</template>

<style scoped>
	a.option-btn {
		text-decoration: none;
		display: inline-block;
		text-align: center;
	}
	.option-btn {
		background: none;
		border: none;
		background-color: blueviolet;
		color: white;
		cursor: pointer;
		height: 30px;
		vertical-align: middle;
	}
	.option-btn-left {
		border-right: 1px solid #ccc;
	}
	.option-btn-right {
		border-right: none;
	}
	.btn-menu {
		position: absolute;
		background-color: white;
		border: 1px solid #ccc;
		padding: 0;
		margin: 0;
		list-style: none;
		width: 150px;
	}
	.btn-menu li {
		padding: 10px;
		cursor: pointer;
	}
	.btn-menu li:hover {
		background-color: #f0f0f0;
	}
</style>
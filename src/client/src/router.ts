import { storeToRefs } from "pinia";
import { createWebHistory, createRouter } from "vue-router";
import TodosList from "./components/todos/TodosList.vue";
import TodoEdit from "./components/todos/TodoEdit.vue";
import Home from "./components/home/StartingPlace.vue";
import UserSignUp from "./components/users/UserSignup.vue";
import SettingsNav from "./components/user_settings/SettingsNav.vue";
import ActionsList from "./components/actions/ActionsList.vue";
import HeroInventory from "./components/item-store/HeroInventory.vue";
import { useCredentialsStore } from "./stores/credentials";




const routes = [
	{ 
		name: "todoEdit", 
		path: "/todos/edit", 
		component: TodoEdit
	},
	{ 
		name: "todoAdd", 
		path: "/todos/add", 
		component: TodoEdit
	},
	{ 
		name: "todos", 
		path: "/todos/list", 
		component: TodosList
	},
	{ 
		name: "userSignup", 
		path: "/user/sign-up", 
		component: UserSignUp
	},
	{ 
		name: "settingsNav", 
		path: "/settings/nav", 
		component: SettingsNav
	},
	{ 
		name: "actions", 
		path: "/actions/list", 
		component: ActionsList
	},
	{ 
		name: "inventory", 
		path: "/hero/inventory", 
		component: HeroInventory
	},
	{ 
		name: "home", 
		path: "/", 
		component: Home,
		meta: {
			public: true
		}
	},
];

const router = createRouter({
	history: createWebHistory(),
	routes
});

router.beforeEach((to) => {
	console.log(to);
	const { credentials } = storeToRefs(useCredentialsStore());
	if (credentials.value.isSignedIn || to.meta.public) {
		return true;
	}
	return { name: "home"};
});

export default router

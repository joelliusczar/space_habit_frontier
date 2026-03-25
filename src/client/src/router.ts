import { storeToRefs } from "pinia";
import { createWebHistory, createRouter } from "vue-router";
import TodosList from "./components/todos/TodosList.vue";
import TodoEdit from "./components/todos/TodoEdit.vue";
import Home from "./components/home/StartingPlace.vue";
import UserSignUp from "./components/users/UserSignup.vue";
import { useCredentialsStore } from "./stores/credentials";




const routes = [
	{ 
		name: "todoEdit", 
		path: "/todo/edit", 
		component: TodoEdit
	},
	{ 
		name: "todoAdd", 
		path: "/todo/add", 
		component: TodoEdit
	},
	{ 
		name: "todos", 
		path: "/todo/list", 
		component: TodosList
	},
	{ 
		name: "userSignup", 
		path: "/user/sign-up", 
		component: UserSignUp
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

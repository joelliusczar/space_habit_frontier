import { createWebHistory, createRouter } from "vue-router";
import TodosList from "./components/todos/TodosList.vue";
import TodoEdit from "./components/todos/TodoEdit.vue";
import Home from "./components/home/StartingPlace.vue";
import UserSignUp from "./components/users/UserSignup.vue";

const routes = [
	{ name: "todoEdit", path: "/todo/edit", component: TodoEdit },
	{ name: "todoAdd", path: "/todo/add", component: TodoEdit },
	{ name: "todos", path: "/todo/list", component: TodosList },
	{ name: "userSignup", path: "/user/sign-up", component: UserSignUp },
	{ name: "home", path: "/", component: Home },
];

export default createRouter({
	history: createWebHistory(),
	routes
});
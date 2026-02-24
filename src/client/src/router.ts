import { createWebHistory, createRouter } from "vue-router";
import TodosList from "./components/todos/TodosList.vue";
import TodoEdit from "./components/todos/TodoEdit.vue";

const routes = [
	{ name: "todoEdit", path: "/todo", component: TodoEdit },
	{ name: "todoAdd", path: "/todo", component: TodoEdit },
	{ name: "home", path: "/", component: TodosList },
];

export default createRouter({
	history: createWebHistory(),
	routes
});
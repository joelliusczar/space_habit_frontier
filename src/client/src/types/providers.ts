import type { InjectionKey  } from "vue";

export interface UserSigninContext {
	openSignin: () => void;
};

export const UserSigninKey: InjectionKey<UserSigninContext> 
	= Symbol("userSignin");
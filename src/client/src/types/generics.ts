export interface Named {
	name: string
};

export interface Titled {
	title: string
};

export type IdValue = number;

export type Token = string


export interface IdItem {
	id: IdValue
};

export interface TokenItem {
	id: Token
};

export interface NamedIdItem extends IdItem, Named {};

export interface TitledTokenItem extends TokenItem, Titled {};

export interface StringObject {
	[key: string]: string
};

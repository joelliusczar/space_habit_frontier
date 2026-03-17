export interface Named {
	name: string
};

export type IdValue = number;


export interface IdItem {
	id: IdValue
};

export interface NamedIdItem extends IdItem, Named {}

export interface StringObject {
	[key: string]: string
};

import {Customer} from "./index.ts";

export default interface Account {
    id: string;
    owner: Customer;
    balance: number;
    createdTs: string;
    updatedTs: string;
}
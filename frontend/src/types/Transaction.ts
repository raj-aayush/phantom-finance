import {Account} from "./index.ts";

export default interface Transaction {
    id: string;
    sender: Account;
    receiver: Account;
    amount: number;
    createdTs: string;
    updatedTs: string;
}
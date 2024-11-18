import {Account} from "./index.ts";

export default interface Transaction {
    id: string;
    sender: Account;
    receiver: Account;
    createdTs: string;
    updatedTs: string;
}
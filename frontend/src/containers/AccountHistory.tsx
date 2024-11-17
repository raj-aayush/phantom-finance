import {Card, Layout, Table, TableProps} from "antd";
import {useEffect, useState} from "react";
import axios from "axios";
import SideMenuWrapper from "./SideMenuWrapper.tsx";
import {Account, Customer, Transaction} from "../types";
import {Link, useParams} from "react-router-dom";

const AccountHistory = () => {
    // const [customer, setCustomer] = useState<Customer>();
    const [balance, setBalance] = useState<number>(0.0);
    const [transactions, setTransactions] = useState<Transaction[]>([]);
    const { customerId, accountId } = useParams();
    useEffect(() => {
        // TODO: Use Redux to store state data
        axios.get('/api/accounts/'+accountId+'/').then(result => {
            setBalance(result.data);
            console.log(result);
        })
        axios.get('/api/accounts/'+accountId+'/history/').then(result => {
            setTransactions(result.data);
            console.log(result);
        })
    }, []);
    const columns: TableProps<Customer>['columns'] = [
        {
            title: 'Transaction ID',
            dataIndex: 'id',
            key: 'id',
            render: (value) => <Link to={"/customers/"+customerId+"/accounts/"+value}>{value}</Link>
        },
        {
            title: 'Sender',
            dataIndex: 'sender',
            key: 'sender',
        },
        {
            title: 'Receiver',
            dataIndex: 'receiver',
            key: 'receiver',
        },
        {
            title: 'Amount',
            dataIndex: 'amount',
            key: 'amount',
        },
        {
            title: 'Transaction date',
            dataIndex: 'createdTs',
            key: 'createdTs',
        }
    ];
    return (
        <SideMenuWrapper>
            <Layout.Content>
                <Card>
                    <Table<Account> columns={columns} dataSource={transactions} />
                </Card>
            </Layout.Content>
        </SideMenuWrapper>
    );
}

export default AccountHistory;
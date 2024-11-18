import {Card, Layout, Table, TableProps} from "antd";
import {useEffect, useState} from "react";
import axios from "axios";
import SideMenuWrapper from "./SideMenuWrapper.tsx";
import {Account, Customer} from "../types";
import {Link, useParams} from "react-router-dom";
import AccountCreate from "./AccountCreate.tsx";

const CustomerAccountsList = () => {
    const [customer, setCustomer] = useState<Customer>();
    const [accounts, setAccounts] = useState<Account[]>([]);
    const { customerId } = useParams();
    useEffect(() => {
        // TODO: Use Redux to store state data
        axios.get('/api/customers/'+customerId+"/").then((result) => {
            setCustomer(result.data);
            console.log(result);
        });
        axios.get('/api/customers/'+customerId+'/accounts/').then(result => {
            setAccounts(result.data);
            console.log(result);
        })
    }, []);
    const columns: TableProps<Customer>['columns'] = [
        {
            title: 'Account ID',
            dataIndex: 'id',
            key: 'id',
            render: (value) => <Link to={"/accounts/"+value}>{value}</Link>
        },
        {
            title: 'Balance',
            dataIndex: 'balance',
            key: 'balance',
        },
        {
            title: 'Creation date',
            dataIndex: 'createdTs',
            key: 'createdTs',
        },
        {
            title: 'Updated date',
            dataIndex: 'updatedTs',
            key: 'updatedTs',
        }
    ];
    return (
        <SideMenuWrapper>
            <Layout.Content>
                <AccountCreate onCreate={account => setAccounts([...accounts, account])} />
                <br />
                <Card>
                    <Table<Account> columns={columns} dataSource={accounts} />
                </Card>
            </Layout.Content>
        </SideMenuWrapper>
    );
}

export default CustomerAccountsList;
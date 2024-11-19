import {Card, Layout, Table, TableProps} from "antd";
import {useEffect, useState} from "react";
import axios from "axios";
import SideMenuWrapper from "./SideMenuWrapper.tsx";
import {Account} from "../types";
import {useParams} from "react-router-dom";
import AccountCreate from "./AccountCreate.tsx";
import UuidRenderer from "../components/UuidRenderer.tsx";
import dayjs from 'dayjs';

const CustomerAccountsList = () => {
    const [accounts, setAccounts] = useState<Account[]>([]);
    const { customerId } = useParams();
    useEffect(() => {
        axios.get('/api/customers/'+customerId+'/accounts/').then(result => {
            setAccounts(result.data);
            console.log(result);
        })
    }, []);
    const columns: TableProps<Account>['columns'] = [
        {
            title: 'Account ID',
            dataIndex: 'id',
            key: 'id',
            render: (value) => <UuidRenderer uuid={value} href={"/accounts/"} />
        },
        {
            title: 'Balance',
            dataIndex: 'balance',
            key: 'balance',
            sorter: (a, b) => a.balance - b.balance,
        },
        {
            title: 'Creation date',
            dataIndex: 'createdTs',
            key: 'createdTs',
            sorter: (a, b) => dayjs(a.createdTs).valueOf() - dayjs(b.createdTs).valueOf(),
        },
        {
            title: 'Updated date',
            dataIndex: 'updatedTs',
            key: 'updatedTs',
            sorter: (a, b) => dayjs(a.updatedTs).valueOf() - dayjs(b.updatedTs).valueOf(),
        }
    ];
    return (
        <SideMenuWrapper>
            <Layout.Content>
                <AccountCreate onCreate={(account: Account) => setAccounts([...accounts, account])} />
                <br />
                <Card>
                    <Table<Account> rowKey="id" columns={columns} dataSource={accounts} />
                </Card>
            </Layout.Content>
        </SideMenuWrapper>
    );
}

export default CustomerAccountsList;
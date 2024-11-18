import {Card, Layout, Table, TableProps} from "antd";
import {useEffect, useState} from "react";
import axios from "axios";
import SideMenuWrapper from "./SideMenuWrapper.tsx";
import {Account, Customer} from "../types";
import {Link} from "react-router-dom";

const AccountsList = () => {
    const [accounts, setAccounts] = useState<Account[]>([]);
    useEffect(() => {
        // TODO: Use Redux to store state data
        axios.get('/api/accounts/').then(result => {
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
            title: 'Owner',
            dataIndex: 'owner',
            key: 'owner.id',
            render: (value) => value.firstName
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
                <Card>
                    <Table<Account> rowKey="id" columns={columns} dataSource={accounts} />
                </Card>
            </Layout.Content>
        </SideMenuWrapper>
    );
}

export default AccountsList;
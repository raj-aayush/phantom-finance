import {Breadcrumb, Card, Layout, Table, TableProps} from "antd";
import {useEffect, useState} from "react";
import axios from "axios";
import SideMenuWrapper from "./SideMenuWrapper.tsx";
import {Account} from "../types";
import {Link, useParams} from "react-router-dom";
import AccountCreate from "./AccountCreate.tsx";
import UuidRenderer from "../components/UuidRenderer.tsx";
import dayjs from 'dayjs';
import RelativeDate from "../components/RelativeDate.tsx";
import {TransactionCreateCard} from "./TransactionCreate.tsx";

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
            render: (value) => <UuidRenderer uuid={value} href={"/customers/"+customerId+"/accounts/"} />
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
            render: (value) => <RelativeDate date={value} />,
            sorter: (a, b) => dayjs(a.createdTs).valueOf() - dayjs(b.createdTs).valueOf(),
        },
        {
            title: 'Updated date',
            dataIndex: 'updatedTs',
            key: 'updatedTs',
            render: (value) => <RelativeDate date={value} />,
            sorter: (a, b) => dayjs(a.updatedTs).valueOf() - dayjs(b.updatedTs).valueOf(),
        }
    ];
    return (
        <SideMenuWrapper>
            <br />
            <Card>
                <Breadcrumb>
                    <Breadcrumb.Item>
                        <Link to={"/customers"}>Customers</Link>
                    </Breadcrumb.Item>
                    <Breadcrumb.Item>
                        <Link to={"/customers/" + customerId}>{customerId?.substring(customerId?.length - 7)} -
                            Accounts</Link>
                    </Breadcrumb.Item>
                </Breadcrumb>
            </Card>
            <br/>
            <Layout.Content>
                <AccountCreate onCreate={(account: Account) => setAccounts([...accounts, account])}/>
                <br/>
                <TransactionCreateCard onComplete={() => {
                    axios.get('/api/customers/'+customerId+'/accounts/').then(result => {
                        setAccounts(result.data);
                        console.log(result);
                    })
                }}/>
                <br />
                <Card>
                    <Table<Account> rowKey="id" columns={columns} dataSource={accounts}/>
                </Card>
            </Layout.Content>
        </SideMenuWrapper>
    );
}

export default CustomerAccountsList;
import {Card, Layout, Table, TableProps} from "antd";
import {useEffect, useState} from "react";
import axios from "axios";
import SideMenuWrapper from "./SideMenuWrapper.tsx";
import {Transaction} from "../types";
import {useParams} from "react-router-dom";
import UuidRenderer from "../components/UuidRenderer.tsx";
import dayjs from "dayjs";

const AccountHistory = () => {
    const [transactions, setTransactions] = useState<Transaction[]>([]);
    const { accountId } = useParams();
    useEffect(() => {
        axios.get('/api/accounts/'+accountId+'/history/').then(result => {
            setTransactions(result.data);
            console.log(result);
        })
    }, []);
    const columns: TableProps<Transaction>['columns'] = [
        {
            title: 'Transaction ID',
            dataIndex: 'id',
            key: 'id',
            render: (value) => <UuidRenderer uuid={value} href={"/customers/"} />
        },
        {
            title: 'Sender',
            dataIndex: 'sender',
            key: 'sender',
            render: (value) => <UuidRenderer uuid={value.id} href={"/accounts/"} />
        },
        {
            title: 'Receiver',
            dataIndex: 'receiver',
            key: 'receiver',
            render: (value) => <UuidRenderer uuid={value.id} href={"/accounts/"} />
        },
        {
            title: 'Amount',
            dataIndex: 'amount',
            key: 'amount',
            sorter: (a, b) => a.amount - b.amount,
        },
        {
            title: 'Transaction date',
            dataIndex: 'createdTs',
            key: 'createdTs',
            sorter: (a, b) => dayjs(a.createdTs).valueOf() - dayjs(b.createdTs).valueOf(),
        }
    ];
    return (
        <SideMenuWrapper>
            <Layout.Content>
                <Card>
                    <Table<Transaction> rowKey="id" columns={columns} dataSource={transactions} />
                </Card>
            </Layout.Content>
        </SideMenuWrapper>
    );
}

export default AccountHistory;
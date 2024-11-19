import {Card, Layout, Table, TableProps} from "antd";
import {useEffect, useState} from "react";
import axios from "axios";
import SideMenuWrapper from "./SideMenuWrapper.tsx";
import {Transaction} from "../types";
import {useParams} from "react-router-dom";
import UuidRenderer from "../components/UuidRenderer.tsx";

const AccountHistory = () => {
    const [transactions, setTransactions] = useState<Transaction[]>([]);
    const { customerId, accountId } = useParams();
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
                    <Table<Transaction> rowKey="id" columns={columns} dataSource={transactions} />
                </Card>
            </Layout.Content>
        </SideMenuWrapper>
    );
}

export default AccountHistory;
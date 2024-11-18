import {Card, Layout, Table, TableProps} from "antd";
import {useEffect, useState} from "react";
import axios from "axios";
import SideMenuWrapper from "./SideMenuWrapper.tsx";
import {Customer} from "../types";
import {Link} from "react-router-dom";

const CustomerList = () => {
    const [customers, setCustomers] = useState<Customer[]>([]);
    useEffect(() => {
        axios.get('/api/customers/').then((result) => {
            setCustomers(result.data);
            console.log(result);
        });
    }, []);
    const columns: TableProps<Customer>['columns'] = [
        {
            title: 'Customer ID',
            dataIndex: 'id',
            key: 'id',
            render: (value) => <Link to={"/customers/"+value}>{value}</Link>
        },
        {
            title: 'Name',
            dataIndex: 'firstName',
            key: 'name',
        },
        {
            title: 'Email',
            dataIndex: 'email',
            key: 'email',
        },
        {
            title: 'Creation date',
            dataIndex: 'createdTs',
            key: 'createdTs',
        }
    ];
    return (
        <SideMenuWrapper>
            <Layout.Content>
                <Card>
                    <Table<Customer> rowKey="id" columns={columns} dataSource={customers} />
                </Card>
            </Layout.Content>
        </SideMenuWrapper>
    );
}

export default CustomerList;
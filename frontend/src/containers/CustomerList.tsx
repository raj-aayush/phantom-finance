import {Breadcrumb, Card, Layout, Table, TableProps} from "antd";
import {useEffect, useState} from "react";
import axios from "axios";
import SideMenuWrapper from "./SideMenuWrapper.tsx";
import {Customer} from "../types";
import UuidRenderer from "../components/UuidRenderer.tsx";
import dayjs from "dayjs";
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
            render: (value) => <UuidRenderer uuid={value} href={"/customers/"} />
        },
        {
            title: 'Name',
            dataIndex: 'firstName',
            key: 'name',
            sorter: (a, b) => a.firstName.localeCompare(b.firstName)
        },
        {
            title: 'Email',
            dataIndex: 'email',
            key: 'email',
            sorter: (a, b) => a.email.localeCompare(b.email)
        },
        {
            title: 'Creation date',
            dataIndex: 'createdTs',
            key: 'createdTs',
            sorter: (a, b) => dayjs(a.createdTs).valueOf() - dayjs(b.createdTs).valueOf(),
        }
    ];
    return (
        <SideMenuWrapper>
            <br />
            <Card>
                <Breadcrumb>
                    <Breadcrumb.Item><Link to={"/customers"}>Customers</Link></Breadcrumb.Item>
                </Breadcrumb>
            </Card>
            <br />
            <Layout.Content>
                <Card>
                    <Table<Customer> rowKey="id" columns={columns} dataSource={customers} />
                </Card>
            </Layout.Content>
        </SideMenuWrapper>
    );
}

export default CustomerList;
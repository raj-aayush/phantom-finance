import {Button, Card, Form, Input, Layout, Select} from "antd";
import {useForm} from "antd/es/form/Form";
import axios from "axios";
import {useParams} from "react-router-dom";
import SideMenuWrapper from "./SideMenuWrapper.tsx";

const TransactionCreate = ({onCreate}) => {
    const [form] = useForm();
    const {accountId} = useParams();
    return (
        <SideMenuWrapper>
            <Layout.Content>
                <Card>
                    <h2>New Transaction</h2>
                    <Form form={form} labelCol={{span: 6}} validateTrigger="onBlur" onFinish={(values) => {
                        axios.post('/api/transactions/', values).then(response => {
                            console.log(response);
                            onCreate(response.data);
                        });
                    }}>
                        <Form.Item name="sender" label="Sender" rules={[{required: true}]}>
                            <Select></Select>
                        </Form.Item>
                        <Form.Item name="receiver" label="Receiver" rules={[{required: true}]}>
                            <Select></Select>
                        </Form.Item>
                        <Form.Item name="amount" label="Amount" normalize={value => parseInt(value, 10)} rules={[{required: true, type: "number", min: 5}]}>
                            <Input type="number"/>
                        </Form.Item>
                        <Form.Item type="submit">
                            <Button type="primary" htmlType="submit">
                                Submit
                            </Button>
                        </Form.Item>
                    </Form>
                </Card>
            </Layout.Content>
        </SideMenuWrapper>
    );
};

export default TransactionCreate;
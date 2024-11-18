import {Button, Card, Form, Input, Layout, Select} from "antd";
import axios from "axios";
import SideMenuWrapper from "./SideMenuWrapper.tsx";

const TransactionCreate = () => {
    const [form] = Form.useForm();
    return (
        <SideMenuWrapper>
            <Layout.Content>
                <Card>
                    <h2>New Transaction</h2>
                    <Form form={form} labelCol={{span: 6}} validateTrigger="onBlur" onFinish={(values) => {
                        axios.post('/api/transactions/', values)
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
                        <Form.Item>
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
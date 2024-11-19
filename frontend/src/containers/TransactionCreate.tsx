import {Button, Card, Form, Input, Layout, Select} from "antd";
import axios from "axios";
import SideMenuWrapper from "./SideMenuWrapper.tsx";

const transactionAccountValidator = (form) => {
    let sender = form.getFieldValue('sender');
    let receiver = form.getFieldValue('receiver');
    if(sender && receiver && sender.toLowerCase() == receiver.toLowerCase()) {
        return Promise.reject("Sender cannot be the same as receiver");
    }
    return Promise.resolve();
}

const TransactionCreate = () => {
    const [form] = Form.useForm();
    return (
        <SideMenuWrapper>
            <Layout.Content>
                <Card>
                    <h2>New Transaction</h2>
                    <Form form={form} labelCol={{span: 6}} onFinish={(values) => {
                        axios.post('/api/transactions/', values)
                    }}>
                        <Form.Item name="sender" label="Sender" rules={[{required: true, validator: () => transactionAccountValidator(form)}]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="receiver" label="Receiver" rules={[{required: true, validator: () => transactionAccountValidator(form)}]}>
                            <Input />
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
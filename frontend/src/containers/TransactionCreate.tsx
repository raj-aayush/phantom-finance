import {Button, Card, Form, FormInstance, Input, Layout, message} from "antd";
import axios from "axios";
import SideMenuWrapper from "./SideMenuWrapper.tsx";

const transactionAccountValidator = (form: FormInstance) => {
    let sender = form.getFieldValue('sender' as any);
    let receiver = form.getFieldValue('receiver' as any);
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
                <Card style={{width: "600px"}}>
                    <h2>New Transaction</h2>
                    <Form form={form} labelCol={{span: 6}} onFinish={(values) => {
                        axios.post('/api/transactions/', values).then(() => {
                            message.success("Completed transaction!");
                            form.resetFields();
                        }).catch((err) => {
                            message.error(err.response.data);
                        });
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
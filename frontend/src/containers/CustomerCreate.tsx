import {Button, Card, Form, Input, Layout} from "antd";
import SideMenuWrapper from "./SideMenuWrapper.tsx";
import axios from "axios";
import {useNavigate} from "react-router-dom";

const CustomerCreate = () => {
    const [form] = Form.useForm();
    const navigate = useNavigate();
    return (
        <SideMenuWrapper>
            <Layout.Content>
                <Card>
                    <h2>New customer details</h2>
                    <Form form={form} labelCol={{span: 6}} onFinish={(values) => {
                        axios.post('/api/customers/', values).then(response => {
                            navigate("/customers");
                        });
                    }}>
                        <Form.Item name="firstName" label="First name" rules={[{required: true}]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="email" label="Email" validateTrigger="onBlur" rules={[{type: "email"}]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="initialAmount" label="Initial amount" normalize={value => parseInt(value, 10)} rules={[{required: true, type: "number", min: 0}]}>
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

export default CustomerCreate;
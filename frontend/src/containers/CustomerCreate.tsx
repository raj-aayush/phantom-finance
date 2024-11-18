import {Button, Card, Form, Input, Layout} from "antd";
import {useForm} from "antd/es/form/Form";
import SideMenuWrapper from "./SideMenuWrapper.tsx";
import axios from "axios";
import {useNavigate} from "react-router-dom";

const CustomerCreate = () => {
    const [form] = useForm();
    const navigate = useNavigate();
    return (
        <SideMenuWrapper>
            <Layout.Content>
                <Card>
                    <h2>New customer details</h2>
                    <Form form={form} labelCol={{span: 6}} validateTrigger="onBlur" onFinish={(values) => {
                        axios.post('/api/customers/', values).then(response => {
                            navigate("/customers");
                        });
                    }}>
                        <Form.Item name="firstName" label="First name" rules={[{required: true}]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="email" label="Email" rules={[{type: "email"}]}>
                            <Input />
                        </Form.Item>
                        <Form.Item name="initialAmount" initialValue="0" label="Initial amount" rules={[{required: true, validator: (_rule, value) => {
                            if(value < 0) {
                                return Promise.reject("Initial amount cannot be a negative number");
                            }
                            return Promise.resolve();
                        }}]}>
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
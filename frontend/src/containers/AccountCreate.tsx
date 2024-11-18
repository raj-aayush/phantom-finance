import {Button, Card, Form, Input} from "antd";
import {useForm} from "antd/es/form/Form";
import axios from "axios";
import {useParams} from "react-router-dom";

const AccountCreate = ({onCreate}) => {
    const [form] = useForm();
    const {customerId} = useParams();
    return (
        <Card>
            <h2>New account</h2>
            <Form layout="inline" form={form} validateTrigger="onBlur" onFinish={(values) => {
                axios.post('/api/customers/'+customerId+'/accounts/', values).then(response => {
                    console.log(response);
                    onCreate(response.data);
                });
            }}>
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
    );
};

export default AccountCreate;
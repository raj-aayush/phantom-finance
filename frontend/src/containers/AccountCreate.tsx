import {Button, Card, Form, Input} from "antd";
import axios from "axios";
import {useParams} from "react-router-dom";

const AccountCreate = ({onCreate}) => {
    const [form] = Form.useForm();
    const {customerId} = useParams();
    return (
        <Card>
            <h2>New account</h2>
            <Form layout="inline" form={form} onFinish={(values) => {
                axios.post('/api/customers/'+customerId+'/accounts/', values).then(response => {
                    console.log(response);
                    onCreate(response.data);
                });
            }}>
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
    );
};

export default AccountCreate;
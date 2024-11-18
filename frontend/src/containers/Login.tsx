import {Button, Card, Form, Input} from "antd";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";


const Login = () => {
    const [form] = Form.useForm();
    const navigate = useNavigate();
    useEffect(() => {
        if(!!localStorage.getItem('token')) {
            navigate("/customers");
        }
    })
    return (
        <div>
            <Card>
                <Form form={form} onFinish={values => {
                    axios.post('/api/login/', values).then(response => {
                        localStorage.setItem('token', response.data);
                        axios.defaults.headers.common['Authorization'] = `Bearer ${response.data}`;
                        navigate("/customers");
                    }
                    ).catch(err => console.log(err));
                }} >
                    <Form.Item name="username" label="Username" rules={[{ required: true }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="password" label="Password" rules={[{ required: true }]}>
                        <Input.Password />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit">
                            Submit
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    );
}

export default Login;
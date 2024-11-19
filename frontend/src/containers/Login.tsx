import {Button, Card, Form, Input, message} from "antd";
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
        <div style={{display: "flex", alignItems: "center", justifyContent: "center", height: '100vh'}}>
            <Card style={{width: "400px"}}>
                <Form form={form} onFinish={values => {
                    axios.post('/api/login/', values)
                        .then(response => {
                            localStorage.setItem('token', response.data);
                            axios.defaults.headers.common['Authorization'] = `Bearer ${response.data}`;
                            navigate("/customers");
                        }).catch(err => {
                            message.error(err.response.data);
                            console.log(err);
                        });
                }} >
                    <Form.Item name="username" label="Username" rules={[{ required: true }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="password" label="Password" rules={[{ required: true }]}>
                        <Input.Password />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit" style={{width: '100%'}}>
                            Sign in
                        </Button>
                    </Form.Item>
                    <Form.Item>
                        <Button style={{width: '100%'}} onClick={() => {
                            form.validateFields().then((values) => {
                                axios.post('/api/register/', values)
                                    .then(_r => {
                                        form.resetFields();
                                        message.success("Registration successful!");
                                    }).catch(err => {
                                        message.error(err.response.data);
                                        console.log(err);
                                    });
                            });
                        }}>
                            Register
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    );
}

export default Login;
import {Layout, Menu} from "antd";
import {ReactNode, useState} from "react";
import {UsergroupAddOutlined} from "@ant-design/icons";


const SideMenuWrapper: ({children}: { children: React.ReactNode }) => JSX.Element = ({children} : {children: ReactNode}) => {
    const [collapsed, setCollapsed] = useState(false);
    return (
        <Layout style={{ minHeight: '100vh', minWidth: '100vw' }}>
            <Layout.Sider collapsible collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
                <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline" items={[
                    {label: "Customers", key: '1', icon: <UsergroupAddOutlined />}
                ]} />
            </Layout.Sider>
            <Layout style={{width: "100%"}}>
                {children}
                <Layout.Footer style={{textAlign: "center"}}>
                    Aayush Raj ©{new Date().getFullYear()}
                </Layout.Footer>
            </Layout>
        </Layout>
    );
}

export default SideMenuWrapper
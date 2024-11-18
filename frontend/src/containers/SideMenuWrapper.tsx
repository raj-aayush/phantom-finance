import {Layout, Menu} from "antd";
import {ReactNode, useState} from "react";
import {
    FileAddOutlined,
    FileTextOutlined,
    TeamOutlined,
    UnorderedListOutlined,
    UserAddOutlined
} from "@ant-design/icons";
import {useNavigate} from "react-router-dom";


const SideMenuWrapper: ({children}: { children: React.ReactNode }) => JSX.Element = ({children} : {children: ReactNode}) => {
    const [collapsed, setCollapsed] = useState(false);
    const navigate = useNavigate();
    console.log(window.location.href.split("/")); // TODO: Set default selected keys based on current URL
    return (
        <Layout style={{ minHeight: '100vh', minWidth: '100vw' }}>
            <Layout.Sider collapsible collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
                <Menu theme="dark" defaultSelectedKeys={['1']} defaultOpenKeys={["customers", "accounts"]} mode="inline" items={[
                    {label: "Customers", key: 'customers', icon: <TeamOutlined />, children: [
                        {label: "All customers", key: "customerList", icon: <UnorderedListOutlined />, onClick: () => navigate("/customers")},
                        {label: "New customer", key: 'addCustomer', icon: <UserAddOutlined />, onClick: () => navigate("/customers/new")}
                    ]},
                    {label: "Accounts", key: 'accounts', icon: <FileTextOutlined />, children: [
                        {label: "All accounts", key: "accountList", icon: <UnorderedListOutlined />, onClick: () => navigate("/accounts")},
                        {label: "New account", key: 'addAccount', icon: <FileAddOutlined />, onClick: () => navigate("/accounts/new")}
                    ]},

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
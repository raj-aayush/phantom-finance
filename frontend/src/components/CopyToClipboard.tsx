import {CopyOutlined} from "@ant-design/icons";
import {Button, message, Popover} from "antd";


const CopyToClipboard = ({popoverText, textToCopy}: {popoverText: string, textToCopy: string}) => {
    return (
        <Popover content={popoverText}>
            <Button style={{padding: "5px"}} onClick={async () => {
                try {
                    await navigator.clipboard.writeText(textToCopy);
                    message.success(`Copied "${textToCopy}" to clipboard!`);
                } catch (err) {
                    console.error('Failed to copy: ', err);
                }
            }}>
                <CopyOutlined />
            </Button>
        </Popover>
    );
}

export default CopyToClipboard;
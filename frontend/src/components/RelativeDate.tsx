import dayjs from "dayjs";
import {Popover} from "antd";
import {InfoCircleOutlined} from "@ant-design/icons";


const RelativeDate = ({date}: {date: string}) => {
    let dayJsDate = dayjs(date);
    return (
        <Popover content={dayJsDate.toString()}>
            {dayJsDate.fromNow()}
            &nbsp;&nbsp;
            <InfoCircleOutlined />
        </Popover>
    );
}

export default RelativeDate;
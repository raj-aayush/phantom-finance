import {Link} from "react-router-dom";
import CopyToClipboard from "./CopyToClipboard.tsx";


const UuidRenderer = ({uuid, href}: {uuid: string}) => {
    return (
        <>
            <Link to={href+uuid}>{uuid.substring(uuid.length-7)}</Link>
            &nbsp;&nbsp;
            <CopyToClipboard popoverText="Copy full SHA to clipboard" textToCopy={uuid} />
        </>
    );
}

export default UuidRenderer;
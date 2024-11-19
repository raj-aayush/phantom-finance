import {Link} from "react-router-dom";
import CopyToClipboard from "./CopyToClipboard.tsx";


const UuidRenderer = ({uuid, href}: {uuid: string, href?: string}) => {
    return (
        <>
            {href
                ? <Link to={href + uuid}><code>{uuid.substring(uuid.length - 7)}</code></Link>
                : <code>{uuid.substring(uuid.length - 7)}</code>
            }
            &nbsp;&nbsp;
            <CopyToClipboard popoverText="Copy full SHA" textToCopy={uuid} />
        </>
    );
}

export default UuidRenderer;
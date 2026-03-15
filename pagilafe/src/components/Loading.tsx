type Props = {
    label?: string;
};

export default function Loading({label = "Loading..."}: Props) {
    return (<div className="state-box loading-box">
            <div className="spinner"/>
            <span>{label}</span>
        </div>);
}
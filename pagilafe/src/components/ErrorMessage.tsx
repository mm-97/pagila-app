type Props = {
    message: string;
};

export default function ErrorMessage({message}: Props) {
    return <div className="state-box state-box--error">{message}</div>;
}
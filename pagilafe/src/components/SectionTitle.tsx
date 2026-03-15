type Props = {
    title: string; subtitle?: string;
};

export default function SectionTitle({title, subtitle}: Props) {
    return (<div className="section-title">
        <h1>{title}</h1>
        {subtitle && <p>{subtitle}</p>}
    </div>);
}
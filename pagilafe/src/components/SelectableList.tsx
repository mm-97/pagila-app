type SelectableListProps<T> = {
    title: string;
    loading: boolean;
    items: T[];
    selectedIds: number[];
    getId: (item: T) => number;
    getLabel: (item: T) => string;
    onToggle: (id: number, nextSelected: boolean) => void;
    helpText?: string;
    loadingText?: string;
};

export default function SelectableList<T>({
                                              title,
                                              loading,
                                              items,
                                              selectedIds,
                                              getId,
                                              getLabel,
                                              onToggle,
                                              helpText,
                                              loadingText = "Loading...",
                                          }: SelectableListProps<T>) {
    return (<div className="form-field form-field--full">
        <span>{title}</span>

        {loading ? (<div className="select-placeholder">{loadingText}</div>) : (<div className="selector-list">
            {items.map((item) => {
                const id = getId(item);
                const selected = selectedIds.includes(id);

                return (<button
                    key={id}
                    type="button"
                    className={`selector-item ${selected ? "selector-item--selected" : ""}`}
                    onClick={() => onToggle(id, !selected)}
                >
                    {getLabel(item)}
                </button>);
            })}
        </div>)}

        {helpText && <small className="form-help">{helpText}</small>}
    </div>);
}
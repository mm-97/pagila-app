type Props = {
    page: number;
    totalPages: number;
    onPrev: () => void;
    onNext: () => void;
};

export default function Pagination({page, totalPages, onPrev, onNext}: Props) {
    return (
        <div className="pagination">
            <button onClick={onPrev} disabled={page === 0}>
                Previous
            </button>
            <span>
        Page {page + 1} / {Math.max(totalPages, 1)}
      </span>
            <button onClick={onNext} disabled={page >= totalPages - 1}>
                Next
            </button>
        </div>
    );
}
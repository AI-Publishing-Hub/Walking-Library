import { Link } from "react-router-dom";
import "./BookCard.css";           // 스타일은 아래 2-3 단계에서

export default function BookCard({ book }) {
  return (
    <Link to={`/books/${book.id}`} className="card">
      <img
        src={book.bookCoverUrl || "https://via.placeholder.com/150x200"}
        alt={book.title}
        className="cover"
      />
      <h3>{book.title}</h3>
      <p className="summary">
        {book.summary ? book.summary.slice(0, 60) + "…" : "요약 준비 중…"}
      </p>
      <p className="price">{book.price ?? "-"} P</p>
    </Link>
  );
}

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

export default function BookDetail() {
  const { id } = useParams();
  const [book, setBook] = useState(null);

  useEffect(() => {
    axios.get(`http://localhost:9000/books/${id}`).then((r) => setBook(r.data));
  }, [id]);

  if (!book) return <p>불러오는 중…</p>;

  return (
    <div style={{ maxWidth: 700, margin: "40px auto" }}>
      <img
        src={book.bookCoverUrl || "https://via.placeholder.com/300x400"}
        alt={book.title}
        style={{ width: 300, float: "left", marginRight: 24 }}
      />
      <h2>{book.title}</h2>
      <p><b>작가 ID</b>: {book.authorId}</p>
      <p><b>가격</b>: {book.price} P</p>
      <h3>요약</h3>
      <p>{book.summary ?? "요약 준비 중…"}</p>
      <hr />
      <h3>본문</h3>
      <p style={{ whiteSpace: "pre-wrap" }}>{book.content}</p>
    </div>
  );
}

import { useEffect, useState } from "react";
import axios from "axios";
import BookCard from "./BookCard";

export default function BookList() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get("http://localhost:9000/books").then((res) => {
      /* HAL or 배열 모두 처리 */
      const list =
        res.data?._embedded?.books ??
        (Array.isArray(res.data) ? res.data : []);
      setBooks(list);
      setLoading(false);
    });
  }, []);

  if (loading) return <p>책 목록을 불러오는 중…</p>;

  return (
    <div className="book-grid">
      {books.map((b) => (
        <BookCard key={b.id} book={b} />
      ))}
    </div>
  );
}

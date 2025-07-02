// src/components/book/BookList.jsx
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import BookCard from './BookCard';
import styles from './BookList.module.css';     // ← 새 CSS Module (그리드)

function BookList() {
  const [books, setBooks]   = useState([]);
  const [loading, setLoad]  = useState(true);

  useEffect(() => {
    (async () => {
      try {
        const res = await axios.get('http://localhost:9000/books');
        if (res.data?._embedded?.books)      setBooks(res.data._embedded.books);
        else if (Array.isArray(res.data))    setBooks(res.data);
      } catch (e) {
        console.error('책 목록 로드 실패', e);
      } finally {
        setLoad(false);
      }
    })();
  }, []);

  if (loading) return <p>책 목록을 불러오는 중…</p>;

  return (
    <section style={{ marginTop: 40 }}>
      <h2>출간된 전체 책 목록</h2>

      {/* 📌 그리드 래퍼 */}
      <div className={styles.grid}>
        {books.map((b) => <BookCard key={b.id} book={b} />)}
      </div>
    </section>
  );
}

export default BookList;

// src/components/book/BookCard.jsx
import React from 'react';
import styles from './BookCard.module.css';       // ← CSS Module import

function BookCard({ book }) {
  return (
    <div className={styles.card}>
      {/* 책 표지 */}
      <img
        className={styles.cover}
        src={book.bookCoverUrl || 'https://via.placeholder.com/150x200'}
        alt={book.title}
      />

      {/* 텍스트 영역 */}
      <div className={styles.body}>
        <h3 className={styles.title}>{book.title}</h3>
        <p className={styles.meta}>작가 ID: {book.authorId}</p>
        <p className={styles.price}>{book.price ?? 0} 포인트</p>
      </div>
    </div>
  );
}

export default BookCard;

import React from 'react';
import { Link } from 'react-router-dom';
import './BookCard.css'; // 이 파일도 생성해야 합니다.

function BookCard({ book }) {
    return (
        <div className="book-card">
            <Link to={`/books/${book.id}`}>
                <img src={book.bookCoverUrl || 'https://via.placeholder.com/150'} alt={book.title} />
                <h3>{book.title}</h3>
                <p>가격: {book.price} 포인트</p>
                {book.isBestseller && <span className="bestseller-badge">베스트셀러</span>}
            </Link>
        </div>
    );
}

export default BookCard;
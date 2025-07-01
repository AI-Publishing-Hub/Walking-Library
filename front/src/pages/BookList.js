import React, { useState, useEffect } from 'react';
import BookCard from '../components/BookCard';
import { getBookSummaries } from '../services/api';
import './BookList.css'; // 이 파일도 생성해야 합니다.

function BookList() {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchBooks = async () => {
            try {
                const data = await getBookSummaries(); // API를 통해 책 목록 GET 요청
                setBooks(data);
            } catch (err) {
                setError('도서 목록을 불러오는 데 실패했습니다.');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchBooks();
    }, []);

    if (loading) {
        return <div>도서 목록 불러오는 중...</div>;
    }

    if (error) {
        return <div className="error-message">{error}</div>;
    }

    return (
        <div className="book-list-container">
            <h2>모든 도서</h2>
            <div className="book-list">
                {books.map((book) => (
                    <BookCard key={book.id} book={book} />
                ))}
            </div>
        </div>
    );
}

export default BookList;
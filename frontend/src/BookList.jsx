import React, { useState, useEffect } from 'react';
import axios from 'axios';

function BookList() {
  const [books, setBooks] = useState([]); // 책 목록을 저장할 상태
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        // 게이트웨이(9000번)를 통해 book-service의 /books API를 호출합니다.
        const response = await axios.get('http://localhost:9000/books');
        if (response.data && response.data._embedded && response.data._embedded.books) 
            {
                setBooks(response.data._embedded.books);
            }
        
    
    } catch (error) {
        console.error("책 목록을 불러오는 데 실패했습니다.", error);
      } finally {
        setLoading(false);
      }
    };

    fetchBooks();
  }, []);

  if (loading) {
    return <p>책 목록을 불러오는 중...</p>;
  }

  // 책 카드 스타일
  const cardStyle = {
    border: '1px solid #ddd',
    borderRadius: '8px',
    padding: '16px',
    margin: '10px',
    width: '200px',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center'
  };

  const imageStyle = {
    width: '150px',
    height: '200px',
    objectFit: 'cover',
    backgroundColor: '#eee'
  };

  return (
    <div style={{ marginTop: '40px' }}>
      <h2>출간된 전체 책 목록</h2>
      <div style={{ display: 'flex', flexWrap: 'wrap' }}>
        {books.map(book => (
          <div key={book.id} style={cardStyle}>
            {/* AI가 생성한 표지 이미지를 보여줍니다 */}
            <img src={book.bookCoverUrl || 'https://via.placeholder.com/150x200'} alt={book.title} style={imageStyle} />
            <h3 style={{ fontSize: '16px', margin: '10px 0' }}>{book.title}</h3>
            <p style={{ fontSize: '14px', color: '#666' }}>작가 ID: {book.authorId}</p>
            <p style={{ fontSize: '14px', color: 'blue' }}>{book.price} 포인트</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default BookList;
import React, { useState } from 'react';
import axios from 'axios';

// 1. props로 { onBookCreated } 를 받도록 수정합니다.
function BookForm({ onBookCreated }) {
  const [bookData, setBookData] = useState({
    title: '',
    content: '',
    authorId: 1,
    isBookPublished: true
  });
  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    setBookData({ ...bookData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('책 등록 처리 중...');
    try {
      const response = await axios.post('http://localhost:9000/books', bookData);
      
      if (response.status === 201) {
        setMessage('새로운 책이 성공적으로 등록되었습니다!');
        setBookData({ title: '', content: '', authorId: 1 });
        
        // 2. 등록 성공 시, 부모로부터 받은 함수를 호출해서 목록 새로고침을 요청합니다.
        if (onBookCreated) {
          onBookCreated();
        }
      }
    } catch (error) {
      setMessage('책 등록 중 오류가 발생했습니다: ' + error.message);
      console.error('API 요청 오류:', error);
    }
  };

  return (
    // ... return 안의 JSX는 기존과 동일 ...
    <div style={{ marginBottom: '40px' }}>
      <h2>새 책 등록하기</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '10px' }}>
          <label>책 제목: </label>
          <input type="text" name="title" value={bookData.title} onChange={handleChange} required style={{width: '300px'}}/>
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label>내용: </label>
          <textarea name="content" value={bookData.content} onChange={handleChange} required style={{width: '300px', height: '100px'}} />
        </div>
        <button type="submit">등록하기</button>
      </form>
      {message && <p style={{ marginTop: '10px', color: 'blue' }}>{message}</p>}
    </div>
  );
}

export default BookForm;
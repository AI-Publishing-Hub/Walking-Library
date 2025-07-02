import { useLocation, useParams, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';

export default function BookDetailPage() {
  const { id } = useParams();
  const nav = useNavigate();
  const location = useLocation();
  const [book, setBook] = useState(location.state || null);

  // 1) 카드에서 넘겨받은 데이터가 없으면 서버에서 다시 조회
  useEffect(() => {
    if (!book) {
      axios.get(`http://localhost:9000/books/${id}`)
        .then(res => setBook(res.data))
        .catch(() => alert('책 정보를 불러오지 못했습니다.'));
    }
  }, [book, id]);

  // 2) 상세 조회 시 조회수 증가
  useEffect(() => {
    axios.post(`http://localhost:9000/books/${id}/view`).catch(()=>{});
  }, [id]);

  if (!book) return <p>불러오는 중...</p>;

  return (
    <div style={{ padding: 24 }}>
      <button onClick={() => nav(-1)}>← 뒤로</button>
      <h1>{book.title}</h1>
      <p>작가 ID: {book.authorId}</p>
      <img
        src={book.bookCoverUrl || 'https://via.placeholder.com/300x400'}
        alt={book.title}
        width={300}
      />
      <pre style={{ whiteSpace: 'pre-wrap' }}>{book.content}</pre>
      <p>조회수: {book.viewCount}</p>
      <p>가격: {book.price ?? '-'} 포인트</p>
    </div>
  );
}

import React, { useState } from 'react'; // useState 임포트
import AuthorForm from './AuthorForm';
import AuthorList from './AuthorList';
import BookList from './BookList';
import BookForm from './BookForm';

function MainPage({ user, onLogout }) {
  // 책 목록을 다시 불러올 시점을 알려주는 '스위치' 상태
  const [refreshKey, setRefreshKey] = useState(0);

  // BookForm에서 책 등록 성공 시 호출될 함수
  const handleBookCreated = () => {
    setRefreshKey(prevKey => prevKey + 1); 
  };

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1>Walking-Library</h1>
        <div>
          <span>{user ? user.name : '사용자'}님 환영합니다.</span>
          <button onClick={onLogout} style={{ marginLeft: '20px' }}>로그아웃</button>
        </div>
      </div>
      
      <hr style={{margin: '20px 0', border: '1px solid #eee'}} />
      
      {/* BookForm에 onBookCreated 라는 이름으로 함수를 전달합니다. */}
      <BookForm onBookCreated={handleBookCreated} />

      {/* BookList에 key 값을 전달하여, key가 바뀔 때마다 새로고침되도록 합니다. */}
      <BookList key={refreshKey} />
      
      <hr style={{margin: '40px 0', border: '1px solid #eee'}} />

      {/* 작가 기능 */}
      <AuthorForm />
      <AuthorList />
    </div>
  );
}

export default MainPage;
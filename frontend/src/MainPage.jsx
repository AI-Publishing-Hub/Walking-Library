import React from 'react';
import AuthorForm from './AuthorForm'; // 기존에 만들었던 작가 신청 폼
import AuthorList from './AuthorList'; // 기존에 만들었던 작가 목록

// user 정보와 onLogout 함수를 props로 받습니다.
function MainPage({ user, onLogout }) {
  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1>Walking-Library</h1>
        <div>
          <span>{user ? user.name : '사용자'}님 환영합니다.</span>
          <button onClick={onLogout} style={{ marginLeft: '20px' }}>로그아웃</button>
        </div>
      </div>
      
      <hr style={{margin: '20px 0'}} />

      {/* 작가 기능 */}
      <AuthorForm />
      <AuthorList />
    </div>
  );
}

export default MainPage;
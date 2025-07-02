// src/pages/HomePage.jsx
import React, { useState, useEffect } from 'react';
import AuthorForm from '../components/author/AuthorForm';
import AuthorList from '../components/author/AuthorList';
import BookForm from '../components/book/BookForm';
import BookList from '../components/book/BookList';
import { useAuth } from '../contexts/AuthContext';

export default function HomePage() {
  const { user, logout } = useAuth();
  const [refreshKey, setRefreshKey] = useState(0);

  const handleBookCreated = () => setRefreshKey((k) => k + 1);

  return (
    <div>
      {/* 헤더 */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1>Walking-Library</h1>
        <div>
          <span>{user?.name}님 환영합니다.</span>
          <button onClick={logout} style={{ marginLeft: 20 }}>로그아웃</button>
        </div>
      </div>

      <hr />

      {/* 책 등록 / 목록 */}
      <BookForm onBookCreated={handleBookCreated} />
      <BookList key={refreshKey} />

      <hr />

      {/* 작가 영역 */}
      <AuthorForm />
      <AuthorList />
    </div>
  );
}

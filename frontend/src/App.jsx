import React, { useState } from 'react';
import LoginPage from './LoginPage'; // 로그인/회원가입 페이지 컴포넌트
import MainPage from './MainPage';   // 로그인 후 보여줄 메인 페이지 컴포넌트
import './App.css'; // 기본 스타일링

function App() {
  // 사용자의 로그인 상태를 관리하는 상태. 기본값은 false (로그아웃 상태)
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [currentUser, setCurrentUser] = useState(null); // 로그인한 사용자 정보 저장

  // 로그인 성공 시 호출될 함수
  const handleLoginSuccess = (userData) => {
    setIsLoggedIn(true);
    setCurrentUser(userData);
  };

  // 로그아웃 시 호출될 함수
  const handleLogout = () => {
    setIsLoggedIn(false);
    setCurrentUser(null);
  };

  return (
    <div className="App">
      {/* isLoggedIn 상태에 따라 다른 컴포넌트를 보여줍니다. */}
      {isLoggedIn ? (
        <MainPage user={currentUser} onLogout={handleLogout} />
      ) : (
        <LoginPage onLoginSuccess={handleLoginSuccess} />
      )}
    </div>
  );
}

export default App;
import React, { useState, useEffect } from 'react'; // useEffect 임포트 추가
import LoginPage from './LoginPage';
import MainPage from './MainPage';
import './App.css';

function App() {
  // 사용자의 로그인 상태를 관리하는 상태
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [currentUser, setCurrentUser] = useState(null);

  // ★ 1. 컴포넌트가 처음 로드될 때 딱 한 번 실행되는 로직
  useEffect(() => {
    // 세션 저장소에서 '로그인 정보'를 확인합니다.
    const loggedInData = sessionStorage.getItem('loggedInUser');
    if (loggedInData) {
      // 저장된 정보가 있다면, 그 정보로 로그인 상태를 복원합니다.
      setIsLoggedIn(true);
      setCurrentUser(JSON.parse(loggedInData));
    }
  }, []); // []가 비어있으면, 최초 1회만 실행됩니다.

  // 로그인 성공 시 호출될 함수
  const handleLoginSuccess = (userData) => {
    setIsLoggedIn(true);
    setCurrentUser(userData);
    // ★ 2. '로그인 정보'를 세션 저장소에 저장합니다.
    sessionStorage.setItem('loggedInUser', JSON.stringify(userData));
  };

  // 로그아웃 시 호출될 함수
  const handleLogout = () => {
    setIsLoggedIn(false);
    setCurrentUser(null);
    // ★ 3. 세션 저장소에서 '로그인 정보'를 삭제합니다.
    sessionStorage.removeItem('loggedInUser');
  };

  return (
    <div className="App">
      {isLoggedIn ? (
        <MainPage user={currentUser} onLogout={handleLogout} />
      ) : (
        <LoginPage onLoginSuccess={handleLoginSuccess} />
      )}
    </div>
  );
}

export default App;
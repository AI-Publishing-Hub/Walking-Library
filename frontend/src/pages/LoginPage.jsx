// src/pages/LoginPage.jsx
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';   // 👀 1) 로그인 후 홈으로 이동하려면

// onLoginSuccess 가 안 내려와도 죽지 않도록 기본값을 줍니다.
function LoginPage({ onLoginSuccess = () => {} }) {
  // ----------------------- 상태 -----------------------
  const [signupData, setSignupData] = useState({
    name: '',
    password: '',
    phoneNumber: '',
  });
  const [loginData, setLoginData] = useState({ userId: '', password: '' });
  const [message,   setMessage]   = useState('');

  const navigate = useNavigate();                 // 👀 2) 페이지 이동용 훅

  // ------------------- 입력 핸들러 --------------------
  const handleSignupChange = e =>
    setSignupData({ ...signupData, [e.target.name]: e.target.value });

  const handleLoginChange  = e =>
    setLoginData({  ...loginData,  [e.target.name]: e.target.value });

  // -------------------- 회원가입 ----------------------
  const handleSignupSubmit = async e => {
    e.preventDefault();
    setMessage('회원가입 처리 중...');
    try {
      await axios.post('http://localhost:9000/users', signupData);
      setMessage(
        "✅ 회원가입 성공! 이제 가입 시 사용한 '이름'을 아이디로 사용하여 로그인하세요."
      );
    } catch (err) {
      const msg = err.response?.data?.message ?? err.message;
      setMessage(`❌ 회원가입 실패: ${msg}`);
    }
  };

  // ---------------------- 로그인 ----------------------
  const handleLoginSubmit = async e => {
    e.preventDefault();
    setMessage('로그인 시도 중...');
    try {
      const res = await axios.post(
        'http://localhost:9000/users/login',
        loginData
      );
      console.log('[LOGIN RES]', res.data);

      onLoginSuccess(res.data);        // 👀 3) 부모(App) 전역 상태에 전달
      setMessage('🎉 로그인 성공! 메인으로 이동합니다.');
      navigate('/');                   // 👀 4) 홈(또는 원하는 경로)으로 이동
    } catch (err) {
      console.error('[LOGIN ERR]', err);
      const msg = err.response?.data ?? err.message ?? '알 수 없는 오류';
      setMessage(`❌ 로그인 실패: ${msg}`);
    }
  };

  // ----------------------- JSX -----------------------
  return (
    <div>
      {/* ================= 회원가입 ================= */}
      <section style={{ marginBottom: 40 }}>
        <h1>회원가입</h1>
        <form onSubmit={handleSignupSubmit}>
          <div>
            <label>이름:&nbsp;</label>
            <input
              name="name"
              value={signupData.name}
              onChange={handleSignupChange}
              required
            />
          </div>
          <div>
            <label>비밀번호:&nbsp;</label>
            <input
              type="password"
              name="password"
              value={signupData.password}
              onChange={handleSignupChange}
              required
            />
          </div>
          <div>
            <label>전화번호:&nbsp;</label>
            <input
              name="phoneNumber"
              value={signupData.phoneNumber}
              onChange={handleSignupChange}
            />
          </div>
          <button type="submit">가입하기</button>
        </form>
      </section>

      <hr />

      {/* ================= 로그인 ================= */}
      <section style={{ marginTop: 40 }}>
        <h1>로그인</h1>
        <form onSubmit={handleLoginSubmit}>
          <div>
            <label>아이디(이름):&nbsp;</label>
            <input
              name="userId"
              value={loginData.userId}
              onChange={handleLoginChange}
              required
            />
          </div>
          <div>
            <label>비밀번호:&nbsp;</label>
            <input
              type="password"
              name="password"
              value={loginData.password}
              onChange={handleLoginChange}
              required
            />
          </div>
          <button type="submit">로그인</button>
        </form>
      </section>

      {message && (
        <p style={{ marginTop: 20, color: 'green', whiteSpace: 'pre-line' }}>
          {message}
        </p>
      )}
    </div>
  );
}

export default LoginPage;

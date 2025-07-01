import React, { useState } from 'react';
import axios from 'axios';

// onLoginSuccess 함수를 App.jsx로부터 받습니다.
function LoginPage({ onLoginSuccess }) {
  const [signupData, setSignupData] = useState({ name: '', password: '', phoneNumber: '' });
  const [loginData, setLoginData] = useState({ userId: '', password: '' });
  const [message, setMessage] = useState('');

  const handleSignupChange = (e) => setSignupData({ ...signupData, [e.target.name]: e.target.value });
  const handleLoginChange = (e) => setLoginData({ ...loginData, [e.target.name]: e.target.value });

  // 회원가입 버튼 클릭 시 실행
  const handleSignupSubmit = async (e) => {
    e.preventDefault();
    setMessage('회원가입 처리 중...');
    try {
      const response = await axios.post('http://localhost:9000/users', signupData);
      setMessage(`회원가입 성공! 이제 가입 시 사용한 '이름'을 아이디로 사용하여 로그인해주세요.`);
    } catch (error) {
      const errorMessage = error.response?.data?.message || error.message;
      setMessage('회원가입 실패: ' + errorMessage);
    }
  };

  // 로그인 버튼 클릭 시 실행
  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    setMessage('로그인 시도 중...');
    try {
      // ★★★ 수정된 부분: API 경로를 '/users/login'으로 정확하게 수정합니다. ★★★
      const response = await axios.post('http://localhost:9000/users/login', loginData);
      
      setMessage('로그인 성공!');
      // 로그인 성공 시, 부모 컴포넌트(App.jsx)에 사용자 정보를 전달합니다.
      onLoginSuccess(response.data);

    } catch (error) {
      // 백엔드가 보내주는 상세 에러 메시지를 표시합니다.
      const errorMessage = error.response?.data || '알 수 없는 오류가 발생했습니다.';
      setMessage('로그인 실패: ' + errorMessage);
    }
  };

  return (
    <div>
      {/* 회원가입 폼 */}
      <div style={{ marginBottom: '40px' }}>
        <h1>회원가입</h1>
        <form onSubmit={handleSignupSubmit}>
          <div><label>이름: </label><input name="name" onChange={handleSignupChange} required /></div>
          <div><label>비밀번호: </label><input type="password" name="password" onChange={handleSignupChange} required /></div>
          <div><label>전화번호: </label><input name="phoneNumber" onChange={handleSignupChange} /></div>
          <button type="submit">가입하기</button>
        </form>
      </div>

      <hr/>

      {/* 로그인 폼 */}
      <div style={{ marginTop: '40px' }}>
        <h1>로그인</h1>
        <form onSubmit={handleLoginSubmit}>
          <div><label>아이디(이름): </label><input name="userId" onChange={handleLoginChange} required /></div>
          <div><label>비밀번호: </label><input type="password" name="password" onChange={handleLoginChange} required /></div>
          <button type="submit">로그인</button>
        </form>
      </div>

      {message && <p style={{ marginTop: '20px', color: 'green' }}>{message}</p>}
    </div>
  );
}

export default LoginPage;
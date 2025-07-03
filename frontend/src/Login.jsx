import React, { useState } from 'react';
import axios from 'axios';

function Login() {
  const [loginData, setLoginData] = useState({ userId: '', password: '' });
  const [loginMessage, setLoginMessage] = useState('');

  const handleLoginChange = (e) => {
    setLoginData({ ...loginData, [e.target.name]: e.target.value });
  };

const handleLoginSubmit = async (e) => {
    e.preventDefault();
    setLoginMessage('로그인 시도 중...');
    try {
      // ★★★ 1. API 경로가 '/users/login'으로 되어 있는지 확인! ★★★
      const response = await axios.post('http://localhost:9000/users/login', loginData);
      
      // 로그인 성공 시, App.jsx로 성공 상태와 사용자 정보를 전달합니다.
      onLoginSuccess(response.data); 
      setLoginMessage('로그인 성공!');
      
    } catch (error) {
      // ★★★ 2. 백엔드가 보내주는 에러 메시지를 정확하게 표시합니다. ★★★
      const errorMessage = error.response?.data || '알 수 없는 오류가 발생했습니다.';
      setLoginMessage('로그인 실패: ' + errorMessage);
    }
};

  return (
    <div style={{ marginBottom: '40px' }}>
      <h2>로그인</h2>
      <form onSubmit={handleLoginSubmit}>
        <div style={{ marginBottom: '10px' }}>
          <label>아이디: </label>
          <input type="text" name="userId" value={loginData.userId} onChange={handleLoginChange} required />
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label>비밀번호: </label>
          <input type="password" name="password" value={loginData.password} onChange={handleLoginChange} required />
        </div>
        <button type="submit">로그인</button>
      </form>
      {loginMessage && <p>{loginMessage}</p>}
    </div>
  );
}

export default Login;
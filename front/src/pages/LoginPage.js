import React, { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { useNavigate, Link } from 'react-router-dom'; // <-- 여기에 Link 추가
import './AuthPage.css'; // 이 파일도 생성 (CSS)


function LoginPage() {
    const [phoneNumber, setPhoneNumber] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            await login(phoneNumber, password);
            navigate('/'); // 로그인 성공 시 메인 페이지로 이동
        } catch (err) {
            setError(err.message || '로그인에 실패했습니다.');
        }
    };

    return (
        <div className="auth-container">
            <h2>로그인</h2>
            <form onSubmit={handleSubmit} className="auth-form">
                <div className="form-group">
                    <label htmlFor="phoneNumber">전화번호</label>
                    <input
                        type="text"
                        id="phoneNumber"
                        value={phoneNumber}
                        onChange={(e) => setPhoneNumber(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">비밀번호</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {error && <p className="error-message">{error}</p>}
                <button type="submit" className="auth-button">로그인</button>
            </form>
            <p>계정이 없으신가요? <Link to="/signup">회원가입</Link></p>
        </div>
    );
}

export default LoginPage;
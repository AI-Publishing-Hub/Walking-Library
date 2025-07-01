import React, { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { useNavigate, Link } from 'react-router-dom';
import './AuthPage.css'; // 이 파일도 생성 (CSS)

function SignUpPage() {
    const [name, setName] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [isKtVerified, setIsKtVerified] = useState(false); // KT 인증 여부
    const [error, setError] = useState('');
    const { signup } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        if (password !== confirmPassword) {
            setError('비밀번호가 일치하지 않습니다.');
            return;
        }
        try {
            await signup(name, phoneNumber, password, isKtVerified);
            alert('회원가입 성공! 로그인되었습니다.');
            navigate('/'); // 회원가입 성공 시 메인 페이지로 이동
        } catch (err) {
            setError(err.message || '회원가입에 실패했습니다.');
        }
    };

    return (
        <div className="auth-container">
            <h2>회원가입</h2>
            <form onSubmit={handleSubmit} className="auth-form">
                <div className="form-group">
                    <label htmlFor="name">이름</label>
                    <input
                        type="text"
                        id="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
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
                <div className="form-group">
                    <label htmlFor="confirmPassword">비밀번호 확인</label>
                    <input
                        type="password"
                        id="confirmPassword"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group checkbox-group">
                    <input
                        type="checkbox"
                        id="isKtVerified"
                        checked={isKtVerified}
                        onChange={(e) => setIsKtVerified(e.target.checked)}
                    />
                    <label htmlFor="isKtVerified">KT 고객 인증 (가입 시 추가 포인트 지급)</label>
                </div>
                {error && <p className="error-message">{error}</p>}
                <button type="submit" className="auth-button">회원가입</button>
            </form>
            <p>이미 계정이 있으신가요? <Link to="/login">로그인</Link></p>
        </div>
    );
}

export default SignUpPage;
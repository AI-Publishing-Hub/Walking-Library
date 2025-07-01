import React, { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { chargePoints, subscribeMember, unsubscribeMember, verifyKt } from '../services/api'; // Mock API 함수
import './MyPage.css'; // 이 파일도 생성 (CSS)

function MyPage() {
    const { member, updateMemberInfo } = useAuth();
    const [chargeAmount, setChargeAmount] = useState(1000); // 포인트 충전 기본값
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');

    if (!member) {
        return <div className="mypage-container"><p>로그인이 필요합니다.</p></div>;
    }

    const handleChargePoints = async () => {
        setError('');
        setMessage('');
        try {
            const response = await chargePoints(member.id, chargeAmount);
            updateMemberInfo({ pointBalance: response.newPointBalance });
            setMessage(`포인트 ${chargeAmount}P 충전 완료! 현재 ${response.newPointBalance}P`);
            setChargeAmount(1000); // 초기화
        } catch (err) {
            setError(err.message || '포인트 충전 실패.');
        }
    };

    const handleSubscribe = async () => {
        setError('');
        setMessage('');
        try {
            const response = await subscribeMember(member.id);
            updateMemberInfo({ subscriptionStatus: response.subscriptionStatus });
            setMessage('월 정액 구독이 신청되었습니다. 무제한 열람을 즐기세요!');
        } catch (err) {
            setError(err.message || '구독 신청 실패.');
        }
    };

    const handleUnsubscribe = async () => {
        setError('');
        setMessage('');
        try {
            const response = await unsubscribeMember(member.id);
            updateMemberInfo({ subscriptionStatus: response.subscriptionStatus });
            setMessage('구독이 해지되었습니다.');
        } catch (err) {
            setError(err.message || '구독 해지 실패.');
        }
    };

    const handleVerifyKt = async () => {
        setError('');
        setMessage('');
        try {
            const response = await verifyKt(member.id);
            updateMemberInfo({ isKtVerified: response.isKtVerified, pointBalance: response.newPointBalance });
            setMessage(`KT 인증 완료! ${response.addedPoints}P가 추가되었습니다. 현재 ${response.newPointBalance}P`);
        } catch (err) {
            setError(err.message || 'KT 인증 실패.');
        }
    };


    return (
        <div className="mypage-container">
            <h2>마이페이지</h2>
            <div className="member-info-summary">
                <p><strong>이름:</strong> {member.name}</p>
                <p><strong>전화번호:</strong> {member.phoneNumber}</p>
                <p><strong>현재 포인트:</strong> {member.pointBalance} P</p>
                <p><strong>구독 상태:</strong> {member.subscriptionStatus === 'SUBSCRIBED' ? '구독 중' : '구독 안함'}</p>
                <p><strong>KT 인증 여부:</strong> {member.isKtVerified ? '인증 완료' : '미인증'}</p>
            </div>

            {message && <p className="action-message">{message}</p>}
            {error && <p className="error-message">{error}</p>}

            {/* 포인트 충전 섹션 */}
            <div className="mypage-section">
                <h3>포인트 충전</h3>
                <input
                    type="number"
                    value={chargeAmount}
                    onChange={(e) => setChargeAmount(Number(e.target.value))}
                    min="1000"
                    step="1000"
                    className="amount-input"
                />
                <button onClick={handleChargePoints} className="action-button">충전하기</button>
            </div>

            {/* 구독 관리 섹션 */}
            <div className="mypage-section">
                <h3>구독 관리</h3>
                {member.subscriptionStatus === 'NOT_SUBSCRIBED' ? (
                    <button onClick={handleSubscribe} className="action-button primary">월 9,900원 요금제 구독 신청</button>
                ) : (
                    <button onClick={handleUnsubscribe} className="action-button secondary">구독 해지</button>
                )}
            </div>

            {/* KT 인증 섹션 */}
            <div className="mypage-section">
                <h3>KT 고객 인증</h3>
                {!member.isKtVerified ? (
                    <button onClick={handleVerifyKt} className="action-button primary">KT 인증하고 5,000P 받기</button>
                ) : (
                    <p>KT 인증이 완료되었습니다.</p>
                )}
            </div>
        </div>
    );
}

export default MyPage;
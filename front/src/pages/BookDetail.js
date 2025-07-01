import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getBookDetail, consumePointsForBookView, getMemberInfo } from '../services/api';
import './BookDetail.css'; // 이 파일도 생성해야 합니다.

function BookDetail() {
    const { id } = useParams(); // URL에서 책 ID를 가져옵니다.
    const [book, setBook] = useState(null);
    const [memberInfo, setMemberInfo] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [message, setMessage] = useState('');

    // 데모를 위해 회원 ID를 하드코딩합니다. 실제 앱에서는 로그인 후 받아올 것입니다.
    const memberId = 1; // 예시 회원 ID

    useEffect(() => {
        const fetchData = async () => {
            try {
                const bookData = await getBookDetail(id); // API를 통해 특정 책 상세 정보 GET 요청
                setBook(bookData);

                const memberData = await getMemberInfo(memberId); // API를 통해 회원 정보 GET 요청
                setMemberInfo(memberData);
            } catch (err) {
                setError('도서 상세 정보 또는 회원 정보를 불러오는 데 실패했습니다.');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id, memberId]);

    // "도서 열람" 버튼 클릭 시 실행될 함수
    const handleViewBook = async () => {
        if (!book || !memberInfo) return;

        // 구독 상태이면 포인트 소모 없이 열람 가능 (가정)
        if (memberInfo.subscriptionStatus === 'SUBSCRIBED') {
            setMessage('구독 중이시므로 포인트를 소모하지 않습니다. 즐겁게 열람하세요!');
            return;
        }

        // 포인트가 부족한 경우
        if (memberInfo.pointBalance < book.price) {
            setMessage(`포인트가 부족합니다! 현재 ${memberInfo.pointBalance} 포인트가 있지만, 이 도서는 ${book.price} 포인트가 필요합니다.`);
            // KT 고객이 아닐 경우 추가 추천
            if (!memberInfo.isKtVerified) {
                setMessage(prev => prev + '\n팁: KT 고객 인증을 통해 추가 포인트를 받거나, 월 정액 구독을 통해 무제한 열람이 가능합니다!');
            } else {
                setMessage(prev => prev + '\n팁: 월 정액 구독을 통해 무제한 열람이 가능합니다!');
            }
            return;
        }

        // 포인트 소모 POST 요청
        try {
            await consumePointsForBookView(memberId, book.price); // API를 통해 포인트 소모 POST 요청
            setMessage(`"${book.title}"을(를) 성공적으로 열람했습니다. ${book.price} 포인트가 차감되었습니다.`);
            // 포인트 잔액 UI 업데이트
            setMemberInfo(prev => ({ ...prev, pointBalance: prev.pointBalance - book.price }));
        } catch (err) {
            setMessage('도서 열람 중 오류가 발생했습니다. 포인트를 차감하는 데 실패했습니다.');
            console.error(err);
        }
    };

    if (loading) {
        return <div>도서 상세 정보 불러오는 중...</div>;
    }

    if (error) {
        return <div className="error-message">{error}</div>;
    }

    if (!book) {
        return <div>도서를 찾을 수 없습니다.</div>;
    }

    return (
        <div className="book-detail-container">
            <h2>{book.title}</h2>
            <img src={book.bookCoverUrl || 'https://via.placeholder.com/250'} alt={book.title} className="book-detail-cover" />
            <p><strong>가격:</strong> {book.price} 포인트</p>
            <p><strong>조회수:</strong> {book.viewCount}</p>
            {book.isBestseller && <span className="bestseller-badge">베스트셀러</span>}
            <h3>책 내용 요약</h3>
            <p>{book.content}</p>

            {memberInfo && (
                <div className="member-info-section">
                    <h3>내 포인트 정보</h3>
                    <p><strong>현재 포인트:</strong> {memberInfo.pointBalance}</p>
                    <p><strong>구독 상태:</strong> {memberInfo.subscriptionStatus}</p>
                </div>
            )}

            <button onClick={handleViewBook} className="view-book-button">
                도서 열람 ( {book.price} 포인트 소모)
            </button>

            {message && <p className="action-message">{message}</p>}
        </div>
    );
}

export default BookDetail;
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getBookDetail, consumePointsForBookView } from '../services/api';
import { useAuth } from '../contexts/AuthContext'; // AuthContext 추가
import './BookDetail.css';

function BookDetail() {
    const { id } = useParams();
    const [book, setBook] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [message, setMessage] = useState('');

    const { member, updateMemberInfo } = useAuth(); // 로그인된 사용자 정보와 업데이트 함수 가져오기

    useEffect(() => {
        const fetchData = async () => {
            try {
                const bookData = await getBookDetail(id);
                setBook(bookData);
            } catch (err) {
                setError('도서 상세 정보를 불러오는 데 실패했습니다.');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [id]);

    const handleViewBook = async () => {
        if (!book) return;

        if (!member) { // 로그인 여부 확인
            setMessage('도서를 열람하려면 로그인이 필요합니다.');
            return;
        }

        if (member.subscriptionStatus === 'SUBSCRIBED') {
            setMessage('구독 중이십니다. 포인트 소모 없이 무제한 열람을 즐기세요!');
            // 실제 백엔드에서는 조회수 증가 등의 로직만 호출
            return;
        }

        if (member.pointBalance < book.price) {
            setMessage(`포인트가 부족합니다! 현재 ${member.pointBalance} 포인트가 있지만, 이 도서는 ${book.price} 포인트가 필요합니다.`);
            if (!member.isKtVerified) {
                setMessage(prev => prev + '\n추천: KT 고객 인증으로 5,000P를 받거나, 월 9,900원 요금제 구독을 고려해보세요.');
            } else {
                setMessage(prev => prev + '\n추천: 월 9,900원 요금제 구독을 고려해보세요.');
            }
            return;
        }

        try {
            // 포인트 소모 Mock API 호출
            const response = await consumePointsForBookView(member.id, book.price);
            updateMemberInfo({ pointBalance: response.newBalance }); // AuthContext의 포인트 잔액 업데이트
            setMessage(`성공적으로 "${book.title}"을 열람했습니다. ${book.price} 포인트가 소모되었습니다.`);

            // 만약 Mock API가 업데이트된 책 정보를 반환한다면, 조회수와 베스트셀러 상태도 업데이트
            if (response.updatedBook) {
                setBook(prevBook => ({
                    ...prevBook,
                    viewCount: response.updatedBook.viewCount,
                    isBestseller: response.updatedBook.isBestseller
                }));
            } else {
                // Mock API가 책 정보를 반환하지 않는 경우, 조회수 수동 증가 시뮬레이션
                setBook(prevBook => ({
                    ...prevBook,
                    viewCount: (prevBook.viewCount || 0) + 1,
                    isBestseller: (prevBook.viewCount || 0) + 1 >= 5 // 베스트셀러 조건
                }));
            }


        } catch (err) {
            setMessage('도서 열람에 실패했습니다. ' + (err.message || '알 수 없는 오류 발생.'));
            console.error(err);
        }
    };

    if (loading) return <div>도서 상세 정보 불러오는 중...</div>;
    if (error) return <div className="error-message">{error}</div>;
    if (!book) return <div>도서를 찾을 수 없습니다.</div>;

    return (
        <div className="book-detail-container">
            <h2>{book.title}</h2>
            <img src={book.bookCoverUrl || 'https://via.placeholder.com/250'} alt={book.title} className="book-detail-cover" />
            <p><strong>가격:</strong> {book.price} 포인트</p>
            <p><strong>조회수:</strong> {book.viewCount}</p>
            {book.isBestseller && <span className="bestseller-badge">베스트셀러</span>}
            <h3>책 내용 요약</h3>
            <p>{book.content}</p>

            {member && ( // 로그인된 경우에만 회원 정보 표시
                <div className="member-info-section">
                    <h3>내 포인트 정보</h3>
                    <p><strong>현재 포인트:</strong> {member.pointBalance} P</p>
                    <p><strong>구독 상태:</strong> {member.subscriptionStatus === 'SUBSCRIBED' ? '구독 중' : '구독 안함'}</p>
                </div>
            )}

            <button onClick={handleViewBook} className="view-book-button">
                도서 열람 ({book.price} 포인트 소모)
            </button>

            {message && <p className="action-message">{message}</p>}
        </div>
    );
}

export default BookDetail;
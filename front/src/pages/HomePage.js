import React, { useState, useEffect } from 'react';
import { getBookSummaries, getBestsellerBooks, getNewReleaseBooks, getBanners } from '../services/api';
import BookCard from '../components/BookCard';
import { Link } from 'react-router-dom';
import './HomePage.css'; // 이 파일도 생성 (CSS)

function HomePage() {
    const [bestsellers, setBestsellers] = useState([]);
    const [newReleases, setNewReleases] = useState([]);
    const [allBooks, setAllBooks] = useState([]);
    const [banners, setBanners] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [bestData, newData, allData, bannerData] = await Promise.all([
                    getBestsellerBooks(),
                    getNewReleaseBooks(),
                    getBookSummaries(),
                    getBanners()
                ]);
                setBestsellers(bestData);
                setNewReleases(newData);
                setAllBooks(allData);
                setBanners(bannerData);
            } catch (err) {
                setError('데이터를 불러오는 데 실패했습니다.');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    if (loading) return <div>메인 화면 불러오는 중...</div>;
    if (error) return <div className="error-message">{error}</div>;

    return (
        <div className="home-page-container">
            {/* 배너 섹션 */}
            <div className="banner-section">
                {banners.map(banner => (
                    <Link to={banner.linkUrl} key={banner.id}>
                        <img src={banner.imageUrl} alt={banner.title} className="banner-image" />
                    </Link>
                ))}
            </div>

            {/* 베스트셀러 섹션 */}
            {bestsellers.length > 0 && (
                <div className="book-section">
                    <h2>이달의 베스트셀러</h2>
                    <div className="book-list horizontal-scroll">
                        {bestsellers.map(book => (
                            <BookCard key={book.id} book={book} />
                        ))}
                    </div>
                    <Link to="/books" className="view-more-link">더보기</Link>
                </div>
            )}

            {/* 신간 도서 섹션 */}
            {newReleases.length > 0 && (
                <div className="book-section">
                    <h2>새로 나온 책</h2>
                    <div className="book-list horizontal-scroll">
                        {newReleases.map(book => (
                            <BookCard key={book.id} book={book} />
                        ))}
                    </div>
                    <Link to="/books" className="view-more-link">더보기</Link>
                </div>
            )}

            {/* 전체 도서 섹션 */}
            {allBooks.length > 0 && (
                <div className="book-section">
                    <h2>모든 도서</h2>
                    <div className="book-list grid-view">
                        {allBooks.map(book => (
                            <BookCard key={book.id} book={book} />
                        ))}
                    </div>
                </div>
            )}
        </div>
    );
}

export default HomePage;
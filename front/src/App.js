// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import HomePage from './pages/HomePage';
import BookList from './pages/BookList';
import BookDetail from './pages/BookDetail';
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';
import MyPage from './pages/MyPage';

import './App.css'; // <-- CSS 임포트는 여기에만 있어야 합니다.

function App() {
    return (
        <Router>
            <div className="App">
                <Header />
                <main className="app-main">
                    <Routes>
                        <Route path="/" element={<HomePage />} />
                        <Route path="/books" element={<BookList />} />
                        <Route path="/books/:id" element={<BookDetail />} />
                        <Route path="/login" element={<LoginPage />} />
                        <Route path="/signup" element={<SignUpPage />} />
                        <Route path="/mypage" element={<MyPage />} />
                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;

// import React from 'react';
// import { Link, useNavigate } from 'react-router-dom';
// import { useAuth } from '../contexts/AuthContext'; // 추가
// // import './Header.css';
// import HomePage from './pages/HomePage';
// import './App.css';
// function Header() {
//     const { member, logout } = useAuth(); // AuthContext에서 member와 logout 함수 가져오기
//     const navigate = useNavigate();

//     const handleLogout = () => {
//         logout();
//         navigate('/login'); // 로그아웃 후 로그인 페이지로 이동
//     };

//     return (
//         <header className="app-header">
//             <Link to="/" className="app-title-link">
//                 <h1>내 도서관</h1>
//             </Link>
//             <nav>
//                 <ul>
//                     {member ? ( // 로그인 상태에 따라 UI 변경
//                         <>
//                             <li>
//                                 <span className="welcome-text">{member.name}님</span>
//                                 <span className="point-info"> ({member.pointBalance} P)</span>
//                             </li>
//                             <li><Link to="/mypage">마이페이지</Link></li>
//                             <li><button onClick={handleLogout} className="logout-button">로그아웃</button></li>
//                         </>
//                     ) : (
//                         <>
//                             <li><Link to="/login">로그인</Link></li>
//                             <li><Link to="/signup">회원가입</Link></li>
//                         </>
//                     )}
//                 </ul>
//             </nav>
//         </header>
//     );
// }

// export default Header;
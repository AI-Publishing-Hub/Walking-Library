import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

function Header() {
    return (
        <header className="app-header">
            <h1>내 간단 도서 앱</h1>
            <nav>
                <ul>
                    <li>
                        <Link to="/">도서 목록</Link>
                    </li>
                </ul>
            </nav>
        </header>
    );
}

export default Header;
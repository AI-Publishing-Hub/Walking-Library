// src/index.js
import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { AuthProvider } from './contexts/AuthContext'; // <-- 이 경로는 './contexts/AuthContext' 여야 합니다.

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <AuthProvider> {/* AuthProvider로 App 컴포넌트 감싸기 */}
            <App />
        </AuthProvider>
    </React.StrictMode>
);
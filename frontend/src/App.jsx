// src/App.jsx
import { Routes, Route, Navigate } from "react-router-dom";
import { useAuth } from "./contexts/AuthContext";

import Layout    from "./components/Layout";
import HomePage  from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";


export default function App() {
  const { user, login } = useAuth();          // ← 로그인 성공 콜백용

  return (
    <Layout>
      <Routes>
        {/* 비로그인 → /login 으로 보냄 */}
        <Route
          path="/"
          element={
            user ? <HomePage /> : <Navigate to="/login" replace />
          }
        />

        {/* 로그인 페이지 (로그인 성공 시 login() 호출) */}
        <Route
          path="/login"
          element={
            user ? (
              <Navigate to="/" replace />
            ) : (
              <LoginPage onLoginSuccess={login} />
            )
          }
        />

        {/* 잘못된 주소 → 홈으로 */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Layout>
  );
}

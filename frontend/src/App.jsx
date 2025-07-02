// src/App.jsx
import { Routes, Route, Navigate, useNavigate } from "react-router-dom";

import { useAuth } from "./contexts/AuthContext";  // (Provider는 main.jsx에서)
import Layout    from "./components/Layout";
import HomePage  from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";

export default function App() {
  const { user, login } = useAuth();   // 현재 로그인 상태 가져오기
  const navigate = useNavigate();      // 페이지 전환용 훅

  /**  로그인 성공 시:
   *   1) 전역 상태에 사용자 저장
   *   2) 루트(/) 로 이동                     */
  const handleLoginSuccess = (u) => {
    login(u);          // AuthContext에 저장
    navigate("/");     // 메인으로!
  };

  return (
    <Layout>
      <Routes>
        {/* ① 로그인했으면 Home, 아니면 /login 으로 보냄 */}
        <Route
          path="/"
          element={user ? <HomePage /> : <Navigate to="/login" replace />}
        />

        {/* ② 로그인 페이지 – 성공 시 handleLoginSuccess 호출 */}
        <Route
          path="/login"
          element={<LoginPage onLoginSuccess={handleLoginSuccess} />}
        />
      </Routes>
    </Layout>
  );
}

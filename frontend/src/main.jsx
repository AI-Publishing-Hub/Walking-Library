// src/main.jsx
import React, { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";

import { AuthProvider } from "./contexts/AuthContext"; // ← 이미 있는 컨텍스트
import App from "./App";

import "./index.css";
import "./App.css";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <AuthProvider>          {/* ① 로그인 정보 전역 */}
      <BrowserRouter>       {/* ② 딱 한 번만 감싸기 */}
        <App />
      </BrowserRouter>
    </AuthProvider>
  </StrictMode>
);

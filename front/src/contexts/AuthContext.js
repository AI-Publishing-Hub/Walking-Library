import React, { createContext, useState, useContext } from 'react';

// 1. Context 생성
export const AuthContext = createContext(null);

// 2. Provider 컴포넌트 생성
export const AuthProvider = ({ children }) => {
    // 초기 Mock 사용자 정보 (로그아웃 상태 또는 기본 사용자)
    const [member, setMember] = useState(
        JSON.parse(localStorage.getItem('member')) || null // 로컬 스토리지에서 정보 로드
    );

    // 로그인 함수 (Mock 데이터 사용)
    const login = async (phoneNumber, password) => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                // 간단한 Mock 로그인 로직
                if (phoneNumber === '010-1111-2222' && password === 'password123') {
                    const loggedInMember = {
                        id: 1,
                        name: '테스트 사용자',
                        phoneNumber: '010-1111-2222',
                        role: 'USER',
                        subscriptionStatus: 'NOT_SUBSCRIBED', // 초기 구독 상태
                        pointBalance: 1000, // 초기 포인트
                        isKtVerified: false, // 초기 KT 인증 상태
                    };
                    setMember(loggedInMember);
                    localStorage.setItem('member', JSON.stringify(loggedInMember)); // 로컬 스토리지에 저장
                    resolve(loggedInMember);
                } else {
                    reject(new Error('전화번호 또는 비밀번호가 올바르지 않습니다.'));
                }
            }, 500);
        });
    };

    // 회원가입 함수 (Mock 데이터 사용)
    const signup = async (name, phoneNumber, password, isKtVerified) => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                // 간단한 Mock 회원가입 로직
                // 실제는 백엔드에서 ID 생성 및 중복 확인
                const newMember = {
                    id: Math.floor(Math.random() * 100000), // 임의의 ID
                    name,
                    phoneNumber,
                    role: 'USER',
                    subscriptionStatus: 'NOT_SUBSCRIBED',
                    pointBalance: 1000 + (isKtVerified ? 5000 : 0), // 가입 시 초기 1000, KT 5000
                    isKtVerified,
                };
                setMember(newMember); // 가입 후 바로 로그인 상태로 간주
                localStorage.setItem('member', JSON.stringify(newMember));
                resolve(newMember);
            }, 500);
        });
    };


    // 로그아웃 함수
    const logout = () => {
        setMember(null);
        localStorage.removeItem('member');
    };

    // 포인트 및 구독 상태 업데이트 함수 (마이페이지 등에서 사용)
    const updateMemberInfo = (newInfo) => {
        setMember(prev => {
            const updated = { ...prev, ...newInfo };
            localStorage.setItem('member', JSON.stringify(updated));
            return updated;
        });
    };

    return (
        <AuthContext.Provider value={{ member, login, signup, logout, updateMemberInfo }}>
            {children}
        </AuthContext.Provider>
    );
};

// 3. Context를 쉽게 사용할 수 있는 Custom Hook
export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};
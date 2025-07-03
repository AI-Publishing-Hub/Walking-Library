import { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext(null);
export const useAuth = () => useContext(AuthContext);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  // 앱 최초 구동 시 세션스토리지에서 복원
  useEffect(() => {
    const saved = sessionStorage.getItem('loggedInUser');
    if (saved) setUser(JSON.parse(saved));
  }, []);

  const login = (u) => {
    setUser(u);
    sessionStorage.setItem('loggedInUser', JSON.stringify(u));
  };

  const logout = () => {
    setUser(null);
    sessionStorage.removeItem('loggedInUser');
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

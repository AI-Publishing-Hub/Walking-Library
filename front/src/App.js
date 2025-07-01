// import logo from './logo.svg';
// import './App.css';

// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }

// export default App;


import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import BookList from './pages/BookList';
import BookDetail from './pages/BookDetail';
import './App.css'; // 이 파일도 생성해야 합니다.

function App() {
    return (
        <Router>
            <div className="App">
                <Header />
                <main className="app-main">
                    <Routes>
                        <Route path="/" element={<BookList />} />
                        <Route path="/books/:id" element={<BookDetail />} />
                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;
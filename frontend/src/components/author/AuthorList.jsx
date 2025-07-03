import React, { useState, useEffect } from 'react';
import axios from 'axios';

function AuthorList() {
  const [authors, setAuthors] = useState([]); // 작가 목록을 저장할 상태
  const [loading, setLoading] = useState(true); // 로딩 상태

  // 이 컴포넌트가 처음 화면에 나타날 때 딱 한 번만 실행되는 함수
  useEffect(() => {
    const fetchAuthors = async () => {
      try {
        // 게이트웨이를 통해 작가 목록 조회 API를 호출합니다. (Read API)
        // Spring Data REST가 자동으로 만든 /authors 엔드포인트를 사용합니다.
        const response = await axios.get('http://localhost:9000/authors');
        
        // _embedded.authors 안에 실제 데이터가 들어있습니다.
        if (response.data && response.data._embedded && response.data._embedded.authors) {
          setAuthors(response.data._embedded.authors);
        }
      } catch (error) {
        console.error("작가 목록을 불러오는 데 실패했습니다.", error);
      } finally {
        setLoading(false); // 로딩 종료
      }
    };

    fetchAuthors();
  }, []); // []가 비어있으면, 최초 1회만 실행됩니다.

  if (loading) {
    return <p>작가 목록을 불러오는 중...</p>;
  }

  return (
    <div style={{ marginTop: '40px' }}>
      <h2>등록된 작가 목록</h2>
      <table border="1" style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
        <thead>
          <tr>
            <th style={{ padding: '8px' }}>아이디</th>
            <th style={{ padding: '8px' }}>이름</th>
            <th style={{ padding: '8px' }}>소개</th>
            <th style={{ padding: '8px' }}>상태</th>
          </tr>
        </thead>
        <tbody>
          {authors.map(author => (
            <tr key={author.id}>
              <td style={{ padding: '8px' }}>{author.id}</td>
              <td style={{ padding: '8px' }}>{author.name}</td>
              <td style={{ padding: '8px' }}>{author.description}</td>
              <td style={{ padding: '8px' }}>{author.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AuthorList;
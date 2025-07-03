import React, { useState } from 'react';
import axios from 'axios';

function AuthorForm() {
  const [formData, setFormData] = useState({
    id: '',
    name: '',
    description: '',
    portfolio: ''
  });
  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('신청 처리 중...');
    try {
      const response = await axios.post(
        'http://localhost:9000/authors/register',
        formData
      );
      if (response.status === 200 || response.status === 201) {
        setMessage('작가 등록 신청이 성공적으로 완료되었습니다! 목록이 곧 업데이트됩니다.');
        setFormData({ id: '', name: '', description: '', portfolio: '' });
      }
    } catch (error) {
      setMessage('오류가 발생했습니다: ' + error.message);
      console.error('API 요청 오류:', error);
    }
  };

  return (
    <div>
      <h1>작가 등록 신청</h1>
      <p>새로운 작가 정보를 입력하고 신청 버튼을 눌러주세요.</p>
      
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '10px' }}>
          <label htmlFor="id" style={{ marginRight: '10px', width: '80px', display: 'inline-block' }}>아이디: </label>
          <input type="text" id="id" name="id" value={formData.id} onChange={handleChange} required />
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label htmlFor="name" style={{ marginRight: '10px', width: '80px', display: 'inline-block' }}>이름: </label>
          <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} required />
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label htmlFor="description" style={{ marginRight: '10px', width: '80px', display: 'inline-block' }}>소개: </label>
          <input type="text" id="description" name="description" value={formData.description} onChange={handleChange} />
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label htmlFor="portfolio" style={{ marginRight: '10px', width: '80px', display: 'inline-block' }}>포트폴리오: </label>
          <input type="text" id="portfolio" name="portfolio" value={formData.portfolio} onChange={handleChange} />
        </div>
        <button type="submit">신청하기</button>
      </form>
      {message && <p style={{ marginTop: '20px', color: 'blue' }}>{message}</p>}
    </div>
  );
}

export default AuthorForm;
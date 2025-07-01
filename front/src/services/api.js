// import axios from 'axios'; // axios는 더 이상 직접 사용하지 않으므로 주석 처리하거나 삭제합니다.

const API_BASE_URL = 'http://localhost:8080/api'; // Mocking 시에는 사실상 사용되지 않음

// 데이터 로딩을 시뮬레이션하기 위한 지연 시간 (밀리초)
const DELAY = 500;

// --- Mock Data ---

// 가짜(Mock) 도서 요약 데이터
const mockBookSummaries = [{
    id: 101,
    title: '앨리스의 이상한 모험',
    authorId: 1,
    isBestseller: true,
    summary: '이상한 나라로 떨어진 앨리스의 환상적인 여정',
    viewCount: 15,
    bookCoverUrl: 'https://cdn.pixabay.com/photo/2016/10/06/17/37/fantasy-1719280_1280.jpg',
    price: 300,
}, {
    id: 102,
    title: '초보자를 위한 React 가이드',
    authorId: 2,
    isBestseller: false,
    summary: 'React 프레임워크의 기초부터 심화까지',
    viewCount: 3,
    bookCoverUrl: 'https://cdn.pixabay.com/photo/2017/08/05/11/16/logo-2582749_1280.png',
    price: 500,
}, {
    id: 103,
    title: '그림으로 배우는 파이썬',
    authorId: 3,
    isBestseller: true,
    summary: '시각적 자료와 함께 파이썬의 핵심을 쉽게 이해',
    viewCount: 8,
    bookCoverUrl: 'https://cdn.pixabay.com/photo/2015/04/23/17/41/javascript-736021_1280.png',
    price: 450,
}, {
    id: 104,
    title: '명상과 마음챙김의 기술',
    authorId: 4,
    isBestseller: false,
    summary: '스트레스 해소를 위한 실용적인 명상 기법',
    viewCount: 2,
    bookCoverUrl: 'https://cdn.pixabay.com/photo/2017/07/31/11/24/leaves-2557551_1280.jpg',
    price: 200,
},
{
    id: 105,
    title: '웹 개발자를 위한 AWS',
    authorId: 5,
    isBestseller: false,
    summary: '클라우드 환경에서 웹 서비스 배포하기',
    viewCount: 1,
    bookCoverUrl: 'https://cdn.pixabay.com/photo/2017/12/03/11/13/cloud-computing-2995642_1280.jpg',
    price: 600,
},
{
    id: 106,
    title: '요리와 여행: 유럽편',
    authorId: 6,
    isBestseller: false,
    summary: '유럽 각국의 대표 요리와 숨겨진 명소',
    viewCount: 0,
    bookCoverUrl: 'https://cdn.pixabay.com/photo/2016/09/01/08/33/italy-1635032_1280.jpg',
    price: 350,
},
];

// 가짜(Mock) 도서 상세 데이터
const mockBookDetails = [{
    id: 101,
    title: '앨리스의 이상한 모험',
    content: '어느 따뜻한 여름날, 앨리스는 언니와 강가에 앉아 무료함을 느끼던 중, 시계를 보며 뛰어가는 흰 토끼를 발견하고 호기심에 그 뒤를 쫓아갑니다. 토끼 굴에 빠진 앨리스는 이상하고 신비로운 세계로 들어가게 되는데, 그곳에서 말하는 동물들, 카드 병사들, 그리고 성미 고약한 하트 여왕을 만나면서 기상천외한 모험을 겪게 됩니다. 이 이야기는 현실과 환상의 경계를 넘나드는 앨리스의 특별한 여정을 담고 있습니다.',
    viewCount: 15,
    authorId: 1,
    isBestseller: true,
    bookCoverUrl: 'https://cdn.pixabay.com/photo/2016/10/06/17/37/fantasy-1719280_1280.jpg',
    price: 300,
}, {
    id: 102,
    title: '초보자를 위한 React 가이드',
    content: '이 책은 React를 처음 배우는 개발자를 위한 완벽한 가이드입니다. JSX 문법부터 컴포넌트 생명 주기, 상태 관리(useState, useEffect), 라우팅(react-router-dom), 그리고 기본적인 API 연동 방법까지, React 개발에 필요한 핵심 개념들을 쉽고 명확하게 설명합니다. 실제 예제와 함께 단계별 학습을 제공하여, 독자들이 스스로 웹 애플리케이션을 구축할 수 있도록 돕습니다.',
    viewCount: 3,
    authorId: 2,
    isBestseller: false,
    bookCoverUrl: 'https://cdn.pixabay.com/photo/2017/08/05/11/16/logo-2582749_1280.png',
    price: 500,
}, {
    id: 103,
    title: '그림으로 배우는 파이썬',
    content: '파이썬 프로그래밍을 그림과 함께 쉽고 재미있게 배울 수 있는 책입니다. 복잡한 개념도 시각적인 자료와 비유를 통해 직관적으로 이해할 수 있도록 구성되어 있습니다. 변수, 조건문, 반복문, 함수, 클래스 등 파이썬의 기본 문법은 물론, 데이터 처리, 웹 스크래핑 등 실용적인 주제까지 다룹니다. 프로그래밍 초보자나 파이썬 입문자에게 강력히 추천합니다.',
    viewCount: 8,
    authorId: 3,
    isBestseller: true,
    bookCoverUrl: 'https://cdn.pixabay.com/photo/2015/04/23/17/41/javascript-736021_1280.png',
    price: 450,
}, {
    id: 104,
    title: '명상과 마음챙김의 기술',
    content: '바쁜 현대 사회에서 스트레스와 불안은 피할 수 없는 동반자입니다. 이 책은 고대부터 전해져 내려오는 명상과 마음챙김의 지혜를 현대인의 삶에 적용하는 방법을 제시합니다. 호흡 명상, 걷기 명상, 몸 스캔 등 다양한 명상 기법을 단계별로 안내하며, 일상 속에서 마음챙김을 실천하는 구체적인 팁을 제공합니다. 마음의 평화를 찾고 싶거나 정신 건강을 증진시키고 싶은 모든 이들에게 도움이 될 것입니다.',
    viewCount: 2,
    authorId: 4,
    isBestseller: false,
    bookCoverUrl: 'https://cdn.pixabay.com/photo/2017/07/31/11/24/leaves-2557551_1280.jpg',
    price: 200,
},
{
    id: 105,
    title: '웹 개발자를 위한 AWS',
    content: '아마존 웹 서비스(AWS)는 클라우드 기반 애플리케이션 개발과 배포를 위한 강력한 도구 모음을 제공합니다. 이 책은 웹 개발자들이 AWS를 활용하여 자신의 서비스를 구축하고 운영하는 방법을 단계별로 안내합니다. EC2, S3, RDS, Lambda, API Gateway 등 핵심 서비스의 개념과 사용법을 익히고, 실제 웹 애플리케이션을 AWS에 배포하는 실습을 통해 클라우드 기반 개발 역량을 강화할 수 있습니다.',
    viewCount: 1,
    authorId: 5,
    isBestseller: false,
    bookCoverUrl: 'https://cdn.pixabay.com/photo/2017/12/03/11/13/cloud-computing-2995642_1280.jpg',
    price: 600,
},
{
    id: 106,
    title: '요리와 여행: 유럽편',
    content: '유럽의 아름다운 도시들을 여행하며 그곳의 대표적인 요리 문화를 탐험하는 특별한 가이드북입니다. 파리의 크루아상, 로마의 파스타, 바르셀로나의 타파스, 베를린의 커리부어스트 등 각 지역의 특색 있는 음식들을 소개하고, 간단하게 만들어 볼 수 있는 레시피도 제공합니다. 미식과 여행을 동시에 즐기고 싶은 분들을 위한 최고의 선택이 될 것입니다.',
    viewCount: 0,
    authorId: 6,
    isBestseller: false,
    bookCoverUrl: 'https://cdn.pixabay.com/photo/2016/09/01/08/33/italy-1635032_1280.jpg',
    price: 350,
},
];

// --- Mock API Functions ---

// GET 요청 시뮬레이션: 모든 책의 요약 정보 반환
export const getBookSummaries = async () => {
    return new Promise((resolve) => {
        setTimeout(() => {
            console.log('Mock: getBookSummaries 반환');
            resolve(mockBookSummaries);
        }, DELAY);
    });
};

// GET 요청 시뮬레이션: 특정 책의 상세 정보 반환
export const getBookDetail = async (id) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            const book = mockBookDetails.find((b) => b.id === Number(id));
            if (book) {
                console.log(`Mock: getBookDetail (ID: ${id}) 반환`, book);
                resolve(book);
            } else {
                console.error(`Mock: getBookDetail (ID: ${id}) - 도서 없음`);
                reject(new Error('도서를 찾을 수 없습니다.'));
            }
        }, DELAY);
    });
};

// GET 요청 시뮬레이션: 회원 정보 반환 (AuthContext에서 관리되므로 직접 사용은 줄어들 것)
export const getMemberInfo = async (memberId) => {
    // 실제 앱에서는 로그인된 사용자의 정보를 가져올 것이므로
    // 이 Mock 함수는 AuthContext의 member 객체를 반환하도록 할 수 있습니다.
    // 현재는 AuthContext에서 직접 관리하므로 이 함수는 사용하지 않을 가능성이 높습니다.
    // 하지만 백엔드 API 명세를 맞추기 위해 남겨둡니다.
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            const storedMember = JSON.parse(localStorage.getItem('member'));
            if (storedMember && storedMember.id === memberId) {
                resolve({ ...storedMember });
            } else {
                reject(new Error('회원을 찾을 수 없습니다.'));
            }
        }, DELAY);
    });
};

// POST 요청 시뮬레이션: 포인트 소모 (실제 포인트는 AuthContext에서 관리)
export const consumePointsForBookView = async (memberId, price) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            let currentMember = JSON.parse(localStorage.getItem('member'));
            if (!currentMember || currentMember.id !== memberId) {
                reject(new Error('인증되지 않은 사용자입니다.'));
                return;
            }

            if (currentMember.subscriptionStatus === 'SUBSCRIBED') {
                // 구독 중이면 포인트 소모 없음
                resolve({ message: "구독 중이므로 포인트 소모 없이 열람합니다.", newBalance: currentMember.pointBalance });
                return;
            }

            if (currentMember.pointBalance >= price) {
                currentMember.pointBalance -= price;
                localStorage.setItem('member', JSON.stringify(currentMember));

                // 도서 조회수 업데이트 및 베스트셀러 여부 확인 (Mock 데이터 직접 수정)
                const bookSummaryIndex = mockBookSummaries.findIndex(b => b.id === mockBookDetails.find(bd => bd.price === price && bd.content)?.id);
                if (bookSummaryIndex !== -1) {
                     mockBookSummaries[bookSummaryIndex].viewCount = (mockBookSummaries[bookSummaryIndex].viewCount || 0) + 1;
                     // 베스트셀러 조건 충족 시 업데이트 (예: 5회 이상)
                     if (mockBookSummaries[bookSummaryIndex].viewCount >= 5) {
                         mockBookSummaries[bookSummaryIndex].isBestseller = true;
                     }
                }
                const bookDetailIndex = mockBookDetails.findIndex(b => b.id === mockBookSummaries.find(bs => bs.price === price)?.id);
                if (bookDetailIndex !== -1) {
                     mockBookDetails[bookDetailIndex].viewCount = (mockBookDetails[bookDetailIndex].viewCount || 0) + 1;
                     if (mockBookDetails[bookDetailIndex].viewCount >= 5) {
                        mockBookDetails[bookDetailIndex].isBestseller = true;
                     }
                }

                resolve({
                    message: '포인트가 성공적으로 차감되었습니다.',
                    newBalance: currentMember.pointBalance,
                    updatedBook: { ...mockBookSummaries[bookSummaryIndex] } // 업데이트된 책 정보도 반환 가능
                });
            } else {
                reject(new Error('포인트가 부족합니다.'));
            }
        }, DELAY);
    });
};

// --- 새로운 API Mock 함수 ---

// 포인트 충전 Mock
export const chargePoints = async (memberId, amount) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            let currentMember = JSON.parse(localStorage.getItem('member'));
            if (!currentMember || currentMember.id !== memberId) {
                reject(new Error('인증되지 않은 사용자입니다.'));
                return;
            }
            currentMember.pointBalance += amount;
            localStorage.setItem('member', JSON.stringify(currentMember));
            resolve({ message: '포인트 충전 성공', newPointBalance: currentMember.pointBalance });
        }, DELAY);
    });
};

// 구독 신청 Mock
export const subscribeMember = async (memberId) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            let currentMember = JSON.parse(localStorage.getItem('member'));
            if (!currentMember || currentMember.id !== memberId) {
                reject(new Error('인증되지 않은 사용자입니다.'));
                return;
            }
            currentMember.subscriptionStatus = 'SUBSCRIBED';
            localStorage.setItem('member', JSON.stringify(currentMember));
            resolve({ message: '구독 신청 성공', subscriptionStatus: 'SUBSCRIBED' });
        }, DELAY);
    });
};

// 구독 해지 Mock
export const unsubscribeMember = async (memberId) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            let currentMember = JSON.parse(localStorage.getItem('member'));
            if (!currentMember || currentMember.id !== memberId) {
                reject(new Error('인증되지 않은 사용자입니다.'));
                return;
            }
            currentMember.subscriptionStatus = 'NOT_SUBSCRIBED';
            localStorage.setItem('member', JSON.stringify(currentMember));
            resolve({ message: '구독 해지 성공', subscriptionStatus: 'NOT_SUBSCRIBED' });
        }, DELAY);
    });
};

// KT 인증 Mock
export const verifyKt = async (memberId) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            let currentMember = JSON.parse(localStorage.getItem('member'));
            if (!currentMember || currentMember.id !== memberId) {
                reject(new Error('인증되지 않은 사용자입니다.'));
                return;
            }
            if (currentMember.isKtVerified) {
                reject(new Error('이미 KT 인증된 사용자입니다.'));
                return;
            }
            currentMember.isKtVerified = true;
            currentMember.pointBalance += 5000; // KT 인증 시 5000포인트 추가
            localStorage.setItem('member', JSON.stringify(currentMember));
            resolve({ message: 'KT 인증 성공', isKtVerified: true, addedPoints: 5000, newPointBalance: currentMember.pointBalance });
        }, DELAY);
    });
};

// 베스트셀러 도서 목록 Mock
export const getBestsellerBooks = async () => {
    return new Promise(resolve => {
        setTimeout(() => {
            console.log('Mock: getBestsellerBooks 반환');
            resolve(mockBookSummaries.filter(book => book.isBestseller));
        }, DELAY);
    });
};

// 신간 도서 목록 Mock
export const getNewReleaseBooks = async () => {
    return new Promise(resolve => {
        setTimeout(() => {
            console.log('Mock: getNewReleaseBooks 반환');
            // ID가 높은 순서 (최근 등록)로 정렬하여 반환
            const sortedBooks = [...mockBookSummaries].sort((a, b) => b.id - a.id);
            resolve(sortedBooks.slice(0, 4)); // 예시로 4개만 반환
        }, DELAY);
    });
};

// 배너/이벤트 정보 Mock
export const getBanners = async () => {
    return new Promise(resolve => {
        setTimeout(() => {
            console.log('Mock: getBanners 반환');
            resolve([
                { id: 1, imageUrl: 'https://via.placeholder.com/800x200?text=Event+1', linkUrl: '/books/101', title: '베스트셀러 할인 이벤트' },
                { id: 2, imageUrl: 'https://via.placeholder.com/800x200?text=New+Arrivals', linkUrl: '/new-releases', title: '새로운 책을 만나보세요' },
                { id: 3, imageUrl: 'https://via.placeholder.com/800x200?text=KT+Membership', linkUrl: '/mypage', title: 'KT 멤버십 혜택!' },
            ]);
        }, DELAY);
    });
};

// // import axios from 'axios'; // axios는 더 이상 직접 사용하지 않으므로 주석 처리하거나 삭제합니다.

// // 백엔드 API 게이트웨이의 기본 URL은 더 이상 사용되지 않지만,
// // 구조를 유지하기 위해 남겨둘 수 있습니다. (실제 배포 시 필요)
// const API_BASE_URL = 'http://localhost:8080/api';

// // 데이터 로딩을 시뮬레이션하기 위한 지연 시간 (밀리초)
// const DELAY = 500;

// // 가짜(Mock) 도서 요약 데이터
// const mockBookSummaries = [{
//     id: 101,
//     title: '앨리스의 이상한 모험',
//     authorId: 1,
//     isBestseller: true,
//     summary: '이상한 나라로 떨어진 앨리스의 환상적인 여정',
//     viewCount: 15,
//     bookCoverUrl: 'https://cdn.pixabay.com/photo/2016/10/06/17/37/fantasy-1719280_1280.jpg',
//     price: 300,
// }, {
//     id: 102,
//     title: '초보자를 위한 React 가이드',
//     authorId: 2,
//     isBestseller: false,
//     summary: 'React 프레임워크의 기초부터 심화까지',
//     viewCount: 3,
//     bookCoverUrl: 'https://cdn.pixabay.com/photo/2017/08/05/11/16/logo-2582749_1280.png',
//     price: 500,
// }, {
//     id: 103,
//     title: '그림으로 배우는 파이썬',
//     authorId: 3,
//     isBestseller: true,
//     summary: '시각적 자료와 함께 파이썬의 핵심을 쉽게 이해',
//     viewCount: 8,
//     bookCoverUrl: 'https://cdn.pixabay.com/photo/2015/04/23/17/41/javascript-736021_1280.png', // 파이썬 이미지가 없어서 임시로 다른 이미지 사용
//     price: 450,
// }, {
//     id: 104,
//     title: '명상과 마음챙김의 기술',
//     authorId: 4,
//     isBestseller: false,
//     summary: '스트레스 해소를 위한 실용적인 명상 기법',
//     viewCount: 2,
//     bookCoverUrl: 'https://cdn.pixabay.com/photo/2017/07/31/11/24/leaves-2557551_1280.jpg',
//     price: 200,
// }, ];

// // 가짜(Mock) 도서 상세 데이터
// const mockBookDetails = [{
//     id: 101,
//     title: '앨리스의 이상한 모험',
//     content: '어느 따뜻한 여름날, 앨리스는 언니와 강가에 앉아 무료함을 느끼던 중, 시계를 보며 뛰어가는 흰 토끼를 발견하고 호기심에 그 뒤를 쫓아갑니다. 토끼 굴에 빠진 앨리스는 이상하고 신비로운 세계로 들어가게 되는데, 그곳에서 말하는 동물들, 카드 병사들, 그리고 성미 고약한 하트 여왕을 만나면서 기상천외한 모험을 겪게 됩니다. 이 이야기는 현실과 환상의 경계를 넘나드는 앨리스의 특별한 여정을 담고 있습니다.',
//     viewCount: 15,
//     authorId: 1,
//     isBestseller: true,
//     bookCoverUrl: 'https://cdn.pixabay.com/photo/2016/10/06/17/37/fantasy-1719280_1280.jpg',
//     price: 300,
// }, {
//     id: 102,
//     title: '초보자를 위한 React 가이드',
//     content: '이 책은 React를 처음 배우는 개발자를 위한 완벽한 가이드입니다. JSX 문법부터 컴포넌트 생명 주기, 상태 관리(useState, useEffect), 라우팅(react-router-dom), 그리고 기본적인 API 연동 방법까지, React 개발에 필요한 핵심 개념들을 쉽고 명확하게 설명합니다. 실제 예제와 함께 단계별 학습을 제공하여, 독자들이 스스로 웹 애플리케이션을 구축할 수 있도록 돕습니다.',
//     viewCount: 3,
//     authorId: 2,
//     isBestseller: false,
//     bookCoverUrl: 'https://cdn.pixabay.com/photo/2017/08/05/11/16/logo-2582749_1280.png',
//     price: 500,
// }, {
//     id: 103,
//     title: '그림으로 배우는 파이썬',
//     content: '파이썬 프로그래밍을 그림과 함께 쉽고 재미있게 배울 수 있는 책입니다. 복잡한 개념도 시각적인 자료와 비유를 통해 직관적으로 이해할 수 있도록 구성되어 있습니다. 변수, 조건문, 반복문, 함수, 클래스 등 파이썬의 기본 문법은 물론, 데이터 처리, 웹 스크래핑 등 실용적인 주제까지 다룹니다. 프로그래밍 초보자나 파이썬 입문자에게 강력히 추천합니다.',
//     viewCount: 8,
//     authorId: 3,
//     isBestseller: true,
//     bookCoverUrl: 'https://cdn.pixabay.com/photo/2015/04/23/17/41/javascript-736021_1280.png',
//     price: 450,
// }, {
//     id: 104,
//     title: '명상과 마음챙김의 기술',
//     content: '바쁜 현대 사회에서 스트레스와 불안은 피할 수 없는 동반자입니다. 이 책은 고대부터 전해져 내려오는 명상과 마음챙김의 지혜를 현대인의 삶에 적용하는 방법을 제시합니다. 호흡 명상, 걷기 명상, 몸 스캔 등 다양한 명상 기법을 단계별로 안내하며, 일상 속에서 마음챙김을 실천하는 구체적인 팁을 제공합니다. 마음의 평화를 찾고 싶거나 정신 건강을 증진시키고 싶은 모든 이들에게 도움이 될 것입니다.',
//     viewCount: 2,
//     authorId: 4,
//     isBestseller: false,
//     bookCoverUrl: 'https://cdn.pixabay.com/photo/2017/07/31/11/24/leaves-2557551_1280.jpg',
//     price: 200,
// }, ];


// // 가짜(Mock) 회원 정보 데이터 (단일 사용자만 가정)
// let mockMemberInfo = { // 'let'으로 변경하여 포인트 잔액을 업데이트할 수 있도록 함
//     id: 1, // 프론트엔드에서 사용되는 하드코딩된 memberId와 일치
//     name: '김철수',
//     phoneNumber: '010-1234-5678',
//     role: 'USER', //
//     subscriptionStatus: 'NOT_SUBSCRIBED', // 초기 상태
//     pointBalance: 1200, // 초기 포인트
//     createdAt: new Date('2023-01-01T10:00:00Z'),
//     updatedAt: new Date('2023-01-01T10:00:00Z'),
//     isKtVerified: false, // 초기 상태
// };

// // GET 요청 시뮬레이션: 모든 책의 요약 정보 반환
// export const getBookSummaries = async () => {
//     return new Promise((resolve) => {
//         setTimeout(() => {
//             console.log('Mock: getBookSummaries 반환');
//             resolve(mockBookSummaries);
//         }, DELAY);
//     });
// };

// // GET 요청 시뮬레이션: 특정 책의 상세 정보 반환
// export const getBookDetail = async (id) => {
//     return new Promise((resolve, reject) => {
//         setTimeout(() => {
//             const book = mockBookDetails.find((b) => b.id === Number(id));
//             if (book) {
//                 console.log(`Mock: getBookDetail (ID: ${id}) 반환`, book);
//                 resolve(book);
//             } else {
//                 console.error(`Mock: getBookDetail (ID: ${id}) - 도서 없음`);
//                 reject(new Error('도서를 찾을 수 없습니다.'));
//             }
//         }, DELAY);
//     });
// };

// // POST 요청 시뮬레이션: 포인트 소모
// export const consumePointsForBookView = async (memberId, price) => {
//     return new Promise((resolve, reject) => {
//         setTimeout(() => {
//             if (mockMemberInfo.id === memberId) {
//                 if (mockMemberInfo.subscriptionStatus === 'SUBSCRIBED') {
//                      // 구독 중이면 포인트 소모 없음
//                     console.log(`Mock: consumePointsForBookView - 회원 ${memberId} 구독 중, 포인트 소모 없음`);
//                     resolve({ message: "구독 중이므로 포인트 소모 없이 열람합니다." });
//                     return;
//                 }
                
//                 if (mockMemberInfo.pointBalance >= price) {
//                     mockMemberInfo.pointBalance -= price;
//                     // 조회수도 업데이트 (선택 사항, DTO에는 있지만 Mock에서는 간단히)
//                     const bookToUpdate = mockBookSummaries.find(b => b.id === mockBookDetails.find(bd => bd.price === price)?.id);
//                     if (bookToUpdate) {
//                          bookToUpdate.viewCount = (bookToUpdate.viewCount || 0) + 1;
//                          // 베스트셀러 조건 충족 시 업데이트 (예: 5회 이상)
//                          if (bookToUpdate.viewCount >= 5) {
//                              bookToUpdate.isBestseller = true;
//                          }
//                     }
//                     console.log(`Mock: consumePointsForBookView - 회원 ${memberId}의 포인트 ${price} 소모. 현재 잔액: ${mockMemberInfo.pointBalance}`);
//                     resolve({
//                         message: '포인트가 성공적으로 차감되었습니다.',
//                         newBalance: mockMemberInfo.pointBalance
//                     });
//                 } else {
//                     console.error(`Mock: consumePointsForBookView - 회원 ${memberId} 포인트 부족. 잔액: ${mockMemberInfo.pointBalance}, 필요: ${price}`);
//                     reject(new Error('포인트가 부족합니다.'));
//                 }
//             } else {
//                 console.error(`Mock: consumePointsForBookView - 회원 ${memberId}를 찾을 수 없음`);
//                 reject(new Error('회원을 찾을 수 없습니다.'));
//             }
//         }, DELAY);
//     });
// };

// // GET 요청 시뮬레이션: 회원 정보 반환
// export const getMemberInfo = async (memberId) => {
//     return new Promise((resolve, reject) => {
//         setTimeout(() => {
//             if (mockMemberInfo.id === memberId) {
//                 console.log(`Mock: getMemberInfo (ID: ${memberId}) 반환`, mockMemberInfo);
//                 resolve({ ...mockMemberInfo
//                 }); // 원본 객체 변경 방지를 위해 복사본 반환
//             } else {
//                 console.error(`Mock: getMemberInfo (ID: ${memberId}) - 회원 없음`);
//                 reject(new Error('회원을 찾을 수 없습니다.'));
//             }
//         }, DELAY);
//     });
// };

// // 추가적인 Mock 데이터 수정 함수 (프론트엔드에서 직접 호출하여 상태 변경 테스트 가능)
// export const mockSubscribeMember = () => {
//     return new Promise(resolve => {
//         setTimeout(() => {
//             mockMemberInfo.subscriptionStatus = 'SUBSCRIBED'; //
//             console.log('Mock: 회원 구독 상태로 변경됨');
//             resolve({ ...mockMemberInfo });
//         }, DELAY);
//     });
// };

// export const mockUnsubscribeMember = () => {
//     return new Promise(resolve => {
//         setTimeout(() => {
//             mockMemberInfo.subscriptionStatus = 'NOT_SUBSCRIBED'; //
//             console.log('Mock: 회원 구독 해지됨');
//             resolve({ ...mockMemberInfo });
//         }, DELAY);
//     });
// };

// export const mockVerifyKt = () => {
//     return new Promise(resolve => {
//         setTimeout(() => {
//             if (!mockMemberInfo.isKtVerified) {
//                 mockMemberInfo.isKtVerified = true; //
//                 mockMemberInfo.pointBalance += 5000; // KT 인증 시 5000포인트 추가
//                 console.log('Mock: KT 인증 완료 및 5000포인트 추가');
//             }
//             resolve({ ...mockMemberInfo });
//         }, DELAY);
//     });
// };

// export const mockChargePoints = (amount) => {
//     return new Promise(resolve => {
//         setTimeout(() => {
//             mockMemberInfo.pointBalance += amount;
//             console.log(`Mock: ${amount} 포인트 충전됨. 현재 잔액: ${mockMemberInfo.pointBalance}`);
//             resolve({ ...mockMemberInfo });
//         }, DELAY);
//     });
// };


// import axios from 'axios';

// // 백엔드 API 게이트웨이의 기본 URL을 여기에 입력하세요.
// // 예: Spring Boot 앱이 8080 포트에서 실행 중이라면 'http://localhost:8080/api'
// // 실제 배포 시에는 이 URL을 실제 백엔드 도메인으로 변경해야 합니다.
// const API_BASE_URL = 'http://localhost:8080/api'; //

// const api = axios.create({
//     baseURL: API_BASE_URL,
//     headers: {
//         'Content-Type': 'application/json',
//     },
// });

// // 모든 책의 요약 정보를 가져오는 GET 요청
// export const getBookSummaries = async () => {
//     try {
//         // /books 엔드포인트에서 책 목록을 가져옵니다.
//         const response = await api.get('/books');
//         return response.data; // BookSummaryDto[] 형태의 데이터 반환
//     } catch (error) {
//         console.error('도서 요약 정보를 가져오는 중 오류 발생:', error);
//         throw error;
//     }
// };

// // 특정 책의 상세 정보를 가져오는 GET 요청
// export const getBookDetail = async (id) => {
//     try {
//         // /books/{id} 엔드포인트에서 특정 책의 상세 정보를 가져옵니다.
//         const response = await api.get(`/books/${id}`);
//         return response.data; // BookDetailDto 형태의 데이터 반환
//     } catch (error) {
//         console.error(`ID ${id}인 도서의 상세 정보를 가져오는 중 오류 발생:`, error);
//         throw error;
//     }
// };

// // 포인트 소모를 위한 POST 요청 (도서 열람 시)
// // 이 요청은 백엔드의 User 서비스에서 포인트를 차감하는 로직을 호출할 것입니다.
// export const consumePointsForBookView = async (memberId, price) => {
//     try {
//         // /users/consume-points 엔드포인트로 memberId와 price를 보냅니다.
//         const response = await api.post('/users/consume-points', {
//             memberId,
//             price
//         });
//         return response.data; // 성공 응답 반환 (예: 업데이트된 포인트 잔액 등)
//     } catch (error) {
//         console.error('포인트 소모 중 오류 발생:', error);
//         throw error;
//     }
// };

// // 회원 정보를 가져오는 GET 요청 (현재 포인트 잔액 확인용)
// export const getMemberInfo = async (memberId) => {
//     try {
//         // /member-info/{id} 엔드포인트에서 회원 정보를 가져옵니다.
//         const response = await api.get(`/member-info/${memberId}`);
//         return response.data; // MemberInfo 형태의 데이터 반환
//     } catch (error) {
//         console.error(`ID ${memberId}인 회원 정보를 가져오는 중 오류 발생:`, error);
//         throw error;
//     }
// };
import http from 'k6/http';
import { check, sleep } from 'k6';

// 테스트할 사이즈
const SIZE = 10;

export const options = {
  vus: 1,
  iterations: 10, // 10번 커서 기반 페이지 요청
};

let cursorCreatedAt = '2024-01-01T00:06:14';
let cursorBoardId = 11;

export default function () {
  let url = `http://localhost:8080/api/community/posts/cursor?size=${SIZE}`;

  if (cursorCreatedAt && cursorBoardId) {
    // ISO 형식으로 만들어줘야 함 (T 포함)
    const encodedDate = encodeURIComponent(cursorCreatedAt);
    url += `&cursorCreatedAt=${encodedDate}&cursorBoardId=${cursorBoardId}`;
  }

  const res = http.get(url);

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}

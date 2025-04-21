import http from 'k6/http';
import { check } from 'k6';

// 설정: 테스트할 페이지 번호와 size
const PAGE = 99999;
const SIZE = 10;

export const options = {
  vus: 1,
  iterations: 10, // 반복 횟수
};

export default function () {
  const url = `http://localhost:8080/api/community/posts?page=${PAGE}&size=${SIZE}`;
  const res = http.get(url);

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}
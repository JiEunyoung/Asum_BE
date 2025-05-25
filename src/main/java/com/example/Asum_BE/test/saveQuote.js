import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  vus: 1, // 동시 가상 유저 수
  iterations: 1, // 총 5번 반복
};

export default function () {
  const url = 'http://localhost:8080/api/quote';

  const payload = JSON.stringify({
    categoryId: 4,
    questionAnswerRequestDtos: [
      { questionId: 5, answerId: 22, otherAnswer: null },
      { questionId: 6, answerId: 31, otherAnswer: null },
      { questionId: 7, answerId: 32, otherAnswer: null },
      { questionId: 8, answerId: 35, otherAnswer: null },
      { questionId: 9, answerId: 43, otherAnswer: null },
      { questionId: 10, answerId: 46, otherAnswer: null },
      { questionId: 11, answerId: 53, otherAnswer: null },
      { questionId: 12, answerId: 54, otherAnswer: null },
      { questionId: 13, answerId: 60, otherAnswer: null },
    ],
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const res = http.post(url, payload, params);

  check(res, {
    'is status 201': (r) => r.status === 201,
    'response time < 1000ms': (r) => r.timings.duration < 1000,
  });

  sleep(1);
}
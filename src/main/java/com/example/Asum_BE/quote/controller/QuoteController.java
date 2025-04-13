package com.example.Asum_BE.quote.controller;

import com.example.Asum_BE.quote.dto.requestDto.UserAnswerRequestDto;
import com.example.Asum_BE.quote.dto.responseDto.QuestionsResponseDto;
import com.example.Asum_BE.quote.dto.responseDto.QuoteListForExpertResponseDto;
import com.example.Asum_BE.quote.dto.responseDto.QuoteListForUserResponseDto;
import com.example.Asum_BE.quote.dto.responseDto.QuoteResponseDto;
import com.example.Asum_BE.quote.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    // 선택한 카테고리에 대한 질문 & 답변 옵션 조회
    @GetMapping("/api/quote/{categoryId}")
    public ResponseEntity<QuestionsResponseDto> findQuestionsAndAnswersById(@PathVariable Long categoryId) {

        return ResponseEntity.ok(quoteService.findQuestionsAndAnswersById(categoryId));
    }

    // 견적서(선택한 답변) 저장
    @PostMapping("/api/quote")
    public ResponseEntity<QuoteResponseDto> saveQuote(@RequestBody UserAnswerRequestDto userAnswerRequestDto) {

        return ResponseEntity.ok(quoteService.saveQuote(userAnswerRequestDto));
    }

    // (회원 입장) 작성한 견적 요청서 목록 조회
    @GetMapping("/api/quote/user")
    public ResponseEntity<List<QuoteListForUserResponseDto>> findAllQuotesForUser() {
        //JWT 로그인 이용 예정
        Long userId = 1L;

        return ResponseEntity.ok(quoteService.findAllQuotesForUser(userId));
    }

    // (회원 입장) 작성한 견적 요청서 상세 조회
    @GetMapping("/api/quote/user/{categoryId}")
    public ResponseEntity<QuoteResponseDto> findQuoteById(@PathVariable Long categoryId) {
        //JWT 로그인 이용 예정
        Long userId = 1L;

        return ResponseEntity.ok(quoteService.findQuoteById(userId, categoryId));
    }

    // (전문가 입장) 받은 견적 요청서 목록 조회
    @GetMapping("/api/quote/expert")
    public ResponseEntity<List<QuoteListForExpertResponseDto>> findAllQuotesForExpert() {
        // JWT 로그인 예정
        Long expertId = 2L;

        return ResponseEntity.ok(quoteService.findAllQuotesForExpert(expertId));
    }

    // (전문가 입장) 받은 견적 요청서 상세 조회
    @GetMapping("/api/quote/expert/{userId}/{categoryId}")
    public ResponseEntity<QuoteResponseDto> findQuoteById(@PathVariable Long userId, @PathVariable Long categoryId) {
        return ResponseEntity.ok(quoteService.findQuoteById(userId, categoryId));
    }
}

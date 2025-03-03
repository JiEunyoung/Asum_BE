package com.example.Asum_BE.quote.controller;

import com.example.Asum_BE.quote.dto.requestDto.UserAnswerRequestDto;
import com.example.Asum_BE.quote.dto.responseDto.QuestionsResponseDto;
import com.example.Asum_BE.quote.dto.responseDto.QuoteResponseDto;
import com.example.Asum_BE.quote.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

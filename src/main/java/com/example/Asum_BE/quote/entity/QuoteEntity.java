package com.example.Asum_BE.quote.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class QuoteEntity {

    private Long quoteId;
    private Long userId;
    private Long categoryId;
    private Long questionId;
    private Long answerId;
    private String otherAnswer;
    private Date createdAt;

    @Builder
    public QuoteEntity(Long quoteId, Long userId, Long categoryId, Long questionId, Long answerId, String otherAnswer, Date createdAt) {
        this.quoteId = quoteId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.questionId = questionId;
        this.answerId = answerId;
        this.otherAnswer = otherAnswer;
        this.createdAt = createdAt;
    }
}

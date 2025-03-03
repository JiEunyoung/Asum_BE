package com.example.Asum_BE.quote.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerEntity {

    private Long questionId;
    private Long categoryId;
    private String answerOption;
}

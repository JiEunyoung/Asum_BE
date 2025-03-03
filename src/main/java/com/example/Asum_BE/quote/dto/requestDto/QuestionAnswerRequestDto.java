package com.example.Asum_BE.quote.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerRequestDto {

    private Long questionId;
    private Long answerId;
    private String otherAnswer;
}

package com.example.Asum_BE.quote.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerResponseDto {

    private String question;
    private String answerOption;
    private String otherAnswer;
}

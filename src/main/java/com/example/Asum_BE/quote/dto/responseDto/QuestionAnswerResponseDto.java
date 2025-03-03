package com.example.Asum_BE.quote.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerResponseDto {

    private String question;
    private List<String> answerOptions;
}

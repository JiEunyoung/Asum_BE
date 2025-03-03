package com.example.Asum_BE.quote.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerRequestDto {

    private Long categoryId;
    private List<QuestionAnswerRequestDto> questionAnswerRequestDtos;
}

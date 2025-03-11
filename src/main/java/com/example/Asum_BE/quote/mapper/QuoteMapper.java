package com.example.Asum_BE.quote.mapper;

import com.example.Asum_BE.quote.dto.responseDto.QuoteResponseDto;
import com.example.Asum_BE.quote.entity.AnswerEntity;
import com.example.Asum_BE.quote.entity.QuestionEntity;
import com.example.Asum_BE.quote.entity.QuoteEntity;
import com.example.Asum_BE.quote.entity.QuoteExpertEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuoteMapper {

    // 카테고리의 질문 조회
    List<QuestionEntity> findQuestionsById(Long categoryId);

    // 카테고리의 답변 조회
    List<AnswerEntity> findAnswerOptionById(Long categoryId);

    //카테고리 이름 조회
    String findCategoryNameById(Long categoryId);

    // 견적서(선택한 답변) 저장
    void saveQuote(List<QuoteEntity> quoteEntities);

    // 견적서(선택한 답변) 조회
    QuoteResponseDto findQuoteById(Long userId, Long categoryId);

    // 견적서 조건에 맞는 성별 조회
    String findGender(Long userId, Long categoryId);

    // 견적서 조건에 맞는 고수 조회
    List<QuoteExpertEntity> findQuoteExpertsById(Map<String, Object> params);
}

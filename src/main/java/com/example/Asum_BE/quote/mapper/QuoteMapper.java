package com.example.Asum_BE.quote.mapper;

import com.example.Asum_BE.quote.dto.responseDto.QuoteListForExpertResponseDto;
import com.example.Asum_BE.quote.dto.responseDto.QuoteListForUserResponseDto;
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

    // 견적서 조건에 맞는 성별 조회
    String findGenderForExpert(Long userId, Long categoryId);

    // 견적서 조건에 맞는 고수 조회
    List<QuoteExpertEntity> findQuoteExpertsById(Map<String, Object> params);

    // (회원 입장) 작성한 견적 요청서 목록 조회
    List<QuoteListForUserResponseDto> findAllQuotesForUser(Long userId);

    // (회원 입장, 전문가 입장) 견적 요청서 상세 조회
    QuoteResponseDto findQuoteById(Long userId, Long categoryId);

    // (전문가 입장) 받은 견적 요청서 목록 조회
    List<QuoteListForExpertResponseDto> findAllQuotesForExpert(Long expertId, Character gender);

    // 전문가의 성별 조회
    Character findGender(Long expertId);
}

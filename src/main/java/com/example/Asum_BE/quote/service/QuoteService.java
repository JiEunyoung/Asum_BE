package com.example.Asum_BE.quote.service;

import com.example.Asum_BE.notification.service.NotificationService;
import com.example.Asum_BE.quote.dto.requestDto.QuestionAnswerRequestDto;
import com.example.Asum_BE.quote.dto.requestDto.UserAnswerRequestDto;
import com.example.Asum_BE.quote.dto.responseDto.QuestionAnswerResponseDto;
import com.example.Asum_BE.quote.dto.responseDto.QuestionsResponseDto;
import com.example.Asum_BE.quote.dto.responseDto.QuoteResponseDto;
import com.example.Asum_BE.quote.entity.AnswerEntity;
import com.example.Asum_BE.quote.entity.QuestionEntity;
import com.example.Asum_BE.quote.entity.QuoteEntity;
import com.example.Asum_BE.quote.mapper.QuoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteMapper quoteMapper;
    private final NotificationService notificationService;

    // 선택한 카테고리에 대한 질문 & 답변 옵션 조회
    public QuestionsResponseDto findQuestionsAndAnswersById(Long categoryId) {

        // 카테고리의 질문 조회
        List<QuestionEntity> questionsByIdEntity = quoteMapper.findQuestionsById(categoryId);

        // 카테고리의 답변 조회
        List<AnswerEntity> answerOptionByIdEntity = quoteMapper.findAnswerOptionById(categoryId);

        // 카테고리 이름 조회
        String categoryNameById = quoteMapper.findCategoryNameById(categoryId);

        // 답변 question_id로 그룹화
        Map<Long, List<AnswerEntity>> answerOptionGroupByQuestion = answerOptionByIdEntity.stream()
                .collect(
                        Collectors.groupingBy(AnswerEntity::getQuestionId)
                );

        // 질문과 답변 매핑
        List<QuestionAnswerResponseDto> questionAnswerResponseDtos = questionsByIdEntity.stream()
                .map(question -> {
                    List<AnswerEntity> answerEntities = answerOptionGroupByQuestion.getOrDefault(question.getQuestionId(), List.of());

                    List<String> answerOptions = answerEntities.stream()
                            .map(AnswerEntity::getAnswerOption)
                            .collect(Collectors.toList());

                    return new QuestionAnswerResponseDto(
                            question.getQuestion(),
                            answerOptions
                    );
                })
                .collect(Collectors.toList());

        return new QuestionsResponseDto(
                categoryNameById,
                questionAnswerResponseDtos
        );
    }

    // 견적서(선택한 답변) 저장
    @Transactional
    public QuoteResponseDto saveQuote(UserAnswerRequestDto userAnswerRequestDto) {

        Long categoryId = userAnswerRequestDto.getCategoryId();
        List<QuestionAnswerRequestDto> questionAnswerRequestDtos = userAnswerRequestDto.getQuestionAnswerRequestDtos();

        List<QuoteEntity> quoteEntities = questionAnswerRequestDtos.stream()
                .map(dto -> {
                    return QuoteEntity.builder()
                            .userId(9L)
                            .categoryId(categoryId)
                            .questionId(dto.getQuestionId())
                            .answerId(dto.getAnswerId())
                            .otherAnswer(dto.getOtherAnswer())
                            .build();
                })
                .collect(Collectors.toList());

        quoteMapper.saveQuote(quoteEntities);

        notificationService.sendQuoteNotification(quoteEntities.get(0), "새로운 견적 요청서가 도착했습니다.");

        return quoteMapper.findQuoteById(9L, categoryId);
    }
}

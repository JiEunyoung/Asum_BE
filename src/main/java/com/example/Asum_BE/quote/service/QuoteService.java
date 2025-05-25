package com.example.Asum_BE.quote.service;

import com.example.Asum_BE.common.exception.InvalidQuoteException;
import com.example.Asum_BE.quote.dto.requestDto.QuestionAnswerRequestDto;
import com.example.Asum_BE.quote.dto.requestDto.UserAnswerRequestDto;
import com.example.Asum_BE.quote.dto.responseDto.*;
import com.example.Asum_BE.quote.entity.AnswerEntity;
import com.example.Asum_BE.quote.entity.QuestionEntity;
import com.example.Asum_BE.quote.entity.QuoteEntity;
import com.example.Asum_BE.quote.event.QuoteSavedEvent;
import com.example.Asum_BE.quote.mapper.QuoteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteMapper quoteMapper;
    private final ApplicationEventPublisher eventPublisher;

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
        long startTime = System.currentTimeMillis();

        Long categoryId = userAnswerRequestDto.getCategoryId();
        List<QuestionAnswerRequestDto> questionAnswerRequestDtos = userAnswerRequestDto.getQuestionAnswerRequestDtos();

        // 선택한 답변으로 견적서 처리
        long quoteStart = System.currentTimeMillis();
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
        log.info("[TIME] quote 저장 완료: {}ms", System.currentTimeMillis() - quoteStart);

        /*
        // 알림 생성, 저장, 전송 동기 처리
        //notificationService.sendQuoteNotification(quoteEntities.get(0), "새로운 견적 요청서가 도착했습니다.");

        // 알림 생성, 저장까지 트랜젝션 안에서 처리 -> 알림 생성, 저장에 문제 발생하면 견적까지 롤백됨
        long notificationSaveStart = System.currentTimeMillis();
        List<NotificationEntity> savedNotifications = notificationService.createAndSaveNotification(quoteEntities.get(0), "새로운 견적 요청서가 도착했습니다.");
        log.info("[TIME] 알림 생성 및 저장 완료: {}ms", System.currentTimeMillis() - notificationSaveStart);

        // 알림 전송 비동기 처리
        long sendStart = System.currentTimeMillis();
        notificationService.sendNotificationAsync(savedNotifications);
        log.info("[TIME] 비동기 알림 전송 제출 완료: {}ms", System.currentTimeMillis() - sendStart);

        log.info("[TOTAL TIME] 전체 saveQuote 처리 완료: {}ms", System.currentTimeMillis() - startTime);
        */

        // 이벤트 발행으로 알림 로직 진행
        eventPublisher.publishEvent(new QuoteSavedEvent(quoteEntities.get(0), "새로운 견적 요청서가 도착했습니다."));

        return quoteMapper.findQuoteById(9L, categoryId);
    }

    // (회원 입장) 작성한 견적 요청서 목록 조회
    public List<QuoteListForUserResponseDto> findAllQuotesForUser(Long userId) {
        return quoteMapper.findAllQuotesForUser(userId);
    }

    // (회원 입장, 전문가 입장) 견적 요청서 상세 조회
    public QuoteResponseDto findQuoteById(Long userId, Long categoryId) {
        QuoteResponseDto quoteResponseDto = quoteMapper.findQuoteById(userId, categoryId);
        if(quoteResponseDto == null) {
            throw new InvalidQuoteException(404, "이미 마감되거나 삭제된 견적 요청서입니다.", HttpStatus.NOT_FOUND);
        }

        return quoteResponseDto;
    }

    // (전문가 입장) 받은 견적 요청서 목록 조회
    public List<QuoteListForExpertResponseDto> findAllQuotesForExpert(Long expertId) {
        Character gender = quoteMapper.findGender(expertId);

        return quoteMapper.findAllQuotesForExpert(expertId, gender);
    }
}

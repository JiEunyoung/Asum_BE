package com.example.Asum_BE.quote.event;

import com.example.Asum_BE.notification.entity.NotificationEntity;
import com.example.Asum_BE.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuoteEventListener {

    private final NotificationService notificationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleQuoteSavedEvent(QuoteSavedEvent event) {
        // 알림 생성
        List<NotificationEntity> quoteNotifications = notificationService.createQuoteNotifications(event.getEntity(), event.getContent());

        // 알림 저장 및 발송
        notificationService.saveAndSendNotification(quoteNotifications);
    }
}

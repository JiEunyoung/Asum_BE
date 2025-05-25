package com.example.Asum_BE.notification.retry;

import com.example.Asum_BE.notification.entity.NotificationEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotificationRetryTask {

    private final NotificationRetryType type;
    private final NotificationEntity notification;
    private final NotificationRetryHandler handler;
    private int retryCount = 0;

    public void incrementRetry() {
        this.retryCount++;
    }

    public boolean isRetryLimitExceeded(int maxRetries) {
        return retryCount >= maxRetries;
    }
}

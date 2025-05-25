package com.example.Asum_BE.notification.retry;

import com.example.Asum_BE.notification.entity.NotificationEntity;

public interface NotificationRetryHandler {

    void retrySave(NotificationEntity notification);
    void retrySend(NotificationEntity notification);
}

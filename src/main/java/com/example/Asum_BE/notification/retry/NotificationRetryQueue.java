package com.example.Asum_BE.notification.retry;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

@Component
@Slf4j
public class NotificationRetryQueue {

    private final BlockingQueue<NotificationRetryTask> retryQueue = new LinkedBlockingQueue<>();
    private static final int MAX_RETRY_COUNT = 5;

    @PostConstruct
    public void startRetry() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            List<NotificationRetryTask> batch = new ArrayList<>();
            retryQueue.drainTo(batch, 100);

            for (NotificationRetryTask task : batch) {
                if(task.isRetryLimitExceeded(MAX_RETRY_COUNT)) {
                    log.error("알림 재시도 횟수 초과: {}", task.getNotification());
                    continue;
                }

                try {
                    if(task.getType() == NotificationRetryType.SAVE) {
                        task.getHandler().retrySave(task.getNotification());
                    } else {
                        task.getHandler().retrySend(task.getNotification());
                    }
                } catch (Exception e) {
                    task.incrementRetry();
                    retryQueue.add(task);
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void add(NotificationRetryTask task) {
        retryQueue.add(task);
    }
}

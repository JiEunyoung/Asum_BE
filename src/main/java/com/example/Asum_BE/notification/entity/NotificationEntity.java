package com.example.Asum_BE.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    private Long notificationId;
    private Long receiverId;
    private String role;
    private String eventType;
    private Long referenceId;
    private String content;
    private Date createdAt;
}

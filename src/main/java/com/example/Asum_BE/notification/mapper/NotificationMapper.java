package com.example.Asum_BE.notification.mapper;

import com.example.Asum_BE.notification.entity.NotificationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {

    void save(NotificationEntity notificationEntity);
    //List<NotificationEntity> findByReceiverId(Long receiverId);
    //void markAsRead(Long notificationId);
    //List<NotificationEntity> findUnreadByReceiverId(Long receiverId);
}

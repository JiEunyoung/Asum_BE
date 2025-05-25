package com.example.Asum_BE.notification.mapper;

import com.example.Asum_BE.notification.entity.NotificationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {

    // 알림 내역 저장
    void save(NotificationEntity notificationEntity);

    // 알림 내역 저장(batch size)
    void saveBatch(List<NotificationEntity> notificationBatchList);

    //List<NotificationEntity> findByReceiverId(Long receiverId);
    //void markAsRead(Long notificationId);
    //List<NotificationEntity> findUnreadByReceiverId(Long receiverId);
}

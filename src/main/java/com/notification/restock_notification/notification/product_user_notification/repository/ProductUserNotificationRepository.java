package com.notification.restock_notification.notification.product_user_notification.repository;

import com.notification.restock_notification.notification.product_user_notification.entity.ProductUserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification,Long> {

    // 재입고 알림 신청한 유저 제일 처음 신청한 순 목록 뽑아오기
    //@Query("SELECT * FROM productUserNotification where has_subscribed = ture AND product.product.id = 1 ORDERBY created_at DESC")
    List<ProductUserNotification> findAllByHasSubscribedAndProduct_IdOrderByCreatedAtDesc(
            boolean hasSubscribed, Long productId);
    Optional<ProductUserNotification> findById(Long aLong);
}

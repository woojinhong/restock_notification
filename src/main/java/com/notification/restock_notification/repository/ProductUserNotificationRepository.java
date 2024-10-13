package com.notification.restock_notification.repository;

import com.notification.restock_notification.controller.ProductUserNotificationController;
import com.notification.restock_notification.domain.ProductUserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification,Long> {

    Optional<ProductUserNotification> findById(Long aLong);
}

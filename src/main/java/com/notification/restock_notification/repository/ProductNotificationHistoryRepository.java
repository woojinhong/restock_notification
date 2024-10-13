package com.notification.restock_notification.repository;

import com.notification.restock_notification.domain.ProductNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductNotificationHistoryRepository extends JpaRepository<ProductNotificationHistory, Long> {


    ProductNotificationHistory findByProductId(Long aLong);
}

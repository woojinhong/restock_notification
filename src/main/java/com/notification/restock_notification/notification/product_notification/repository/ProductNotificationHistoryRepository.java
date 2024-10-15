package com.notification.restock_notification.notification.product_notification.repository;

import com.notification.restock_notification.notification.product_notification.entity.ProductNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductNotificationHistoryRepository extends JpaRepository<ProductNotificationHistory, Long> {


    @Query("SELECT MAX(p.restockVer) FROM ProductNotificationHistory p WHERE p.product.id = :productId")
    Optional<Integer> findLatestRestockVerByProductId(@Param("productId") Long productId);
    ProductNotificationHistory findByProductId(Long aLong);
}

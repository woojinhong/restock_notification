package com.notification.restock_notification.notification.product_user_notification.entity;


import com.notification.restock_notification.notification.product_notification.entity.ProductNotificationHistory;
import com.notification.restock_notification.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * 각 상품 회차별 유저의 상태를 CREATED AT DESC 순으로 저장 -> ERROR 발생시 LATEST 순으로 추적하여 재전송
 */
@Entity
@NoArgsConstructor
@Getter

@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"productNotificationHistoryId", "userId"})
        }
)
public class ProductUserNotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productNotificationHistoryId", nullable = false)
    private ProductNotificationHistory productNotificationHistory;

    public ProductUserNotificationHistory(Long userId, ProductNotificationHistory productNotificationHistory) {
        this.userId = userId;
        this.productNotificationHistory = productNotificationHistory;

    }
}

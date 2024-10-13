package com.notification.restock_notification.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
public class ProductUserNotificationHistory extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productNotificationHistoryId", nullable = false)
    private ProductNotificationHistory productNotificationHistory;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;




}

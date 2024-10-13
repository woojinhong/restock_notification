package com.notification.restock_notification.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 각 상품 회차별 유저의 알림 신청 여부 판독 테이블
 */
@Entity
@NoArgsConstructor
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "restock_ver", "user_id"})
        }
)
public class ProductUserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int restockVer;

    // 알림 신청 여부
    @Column(nullable = false)
    private boolean hasSubscribed;



}

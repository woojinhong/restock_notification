package com.notification.restock_notification.notification.product_user_notification.entity;

import com.notification.restock_notification.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 각 상품 회차별 유저의 알림 신청 여부 판독 테이블
 */
@Entity
@NoArgsConstructor
@Getter
@Access(AccessType.FIELD)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "user_id"})
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

    // 알림 신청 여부
    @Column(nullable = false)
    private boolean hasSubscribed;


    // testcase 용
    public ProductUserNotification(Long userId, Product product, boolean hasSubscribed) {
        this.userId = userId;
        this.product = product;

        this.hasSubscribed = hasSubscribed;
    }


}

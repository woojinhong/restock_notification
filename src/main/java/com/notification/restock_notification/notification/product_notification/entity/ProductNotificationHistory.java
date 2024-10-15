package com.notification.restock_notification.notification.product_notification.entity;

import com.notification.restock_notification.common.entity.TimeStamped;
import com.notification.restock_notification.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * 회차별 알림의 복잡성 문제
 * 1. 고민점 실무에서 회차 개념을 왜 사용하는지? 아니면 이 기업과제 한정인지?
 *
 * -재고가 부족해지면 알림을 중단하고,
 *  다음 재입고 시 중단된 지점부터 알림을 재개하면 충분합니다.
 *
 * -매 재입고 시마다 알림 상태를 회차별로 관리하는 것은
 *  복잡성을 증가시키고 불필요한 중복 데이터를 야기할 수 있습니다.
 *
 * -실제로 서비스에서는 재입고가 될 때마다 알림을 재개하면 사용자의 경험에 문제가 생기지 않습니다. 단순히 알림을 중단된 시점에서 이어서 진행하면 효율적입니다.
 */


/**
 * 각 상품별 재입고 회차 상태 여부 저장 테이블
 * 예) (A,1) = CANCELED BY ERROR
 *     (A,2) = COMPLETED
 *     (A,3) = STOCK 부재
 *     (A,4) = IN_PROGRESS
 */

@Entity
@NoArgsConstructor
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "restock_ver"})
        }
)
public class ProductNotificationHistory extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 회차별 알림 전송 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // 재입고 회차
    @Column(nullable = false)
    private int restockVer;

    public void changeStatus(Status status) {
        this.status = status;
    }

    public ProductNotificationHistory(Product product, Status status, int restockVer) {
        this.product = product;
        this.status = status;
        this.restockVer = restockVer;
    }
}

package com.notification.restock_notification.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int stock;

    // 재입고 회차
    @Column(nullable = false)
    private int currentRestockVersion;

    public Product(int stock) {
        this.stock = stock;
        this.currentRestockVersion = 0;
    }

    // 1차 과제에서 피드백을 받았던 set 메서드 대신 메서드나, static으로 의도 파악을 더 명확히 하여 구분.
    // 재고 증가 로직만 단순히 포함
    public void addStock(int amount) {
        this.stock += amount;
    }
    // 재입고 회차 증가
    public void increaseRestockVer() {
        this.currentRestockVersion++;
    }
}

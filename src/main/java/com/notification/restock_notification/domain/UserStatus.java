package com.notification.restock_notification.domain;




/**
 * productUserNotificationHistory (상품 + 유저별 알림 히스토리)
 *
 * 특정 상품별 회차의 유저 상태를 저장
 * 예)
 *
 * (상품, 유저,  상태)
 * ( 1,   1,  PENDING)
 *
 */
public enum UserStatus {
    PENDING, SUCCESS, FAILED
}

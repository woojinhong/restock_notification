package com.notification.restock_notification.domain;

/*
- IN_PROGRESS (발송 중)
- CANCELED_BY_SOLD_OUT (품절에 의한 발송 중단)
- CANCELED_BY_ERROR (예외에 의한 발송 중단)
- COMPLETED (완료)
 */
public enum Status {
    IN_PROGRESS, CANCELED_BY_SOLD_OUT, CANCELED_BY_ERROR, COMPLETED

}

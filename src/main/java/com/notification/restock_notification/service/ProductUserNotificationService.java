package com.notification.restock_notification.service;

import com.notification.restock_notification.domain.Product;
import com.notification.restock_notification.domain.ProductNotificationHistory;
import com.notification.restock_notification.domain.ProductUserNotification;
import com.notification.restock_notification.repository.ProductNotificationHistoryRepository;
import com.notification.restock_notification.repository.ProductRepository;
import com.notification.restock_notification.repository.ProductUserNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductUserNotificationService {

    private static int MSG_ALERT_CNT = 500;
    private final ProductUserNotificationRepository userNotifyRepository;
    private final ProductNotificationHistoryRepository notifyRepository;
    private final ProductRepository productRepository;

    /*
- IN_PROGRESS (발송 중)
- CANCELED_BY_SOLD_OUT (품절에 의한 발송 중단)
- CANCELED_BY_ERROR (예외에 의한 발송 중단)
- COMPLETED (완료)
*/
    // 재입고 알림 전송 API
    public ResponseEntity<?> sendNotify(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("해당 상품 없음"));
        // 재입고 알림 정보 가져오기
        ProductNotificationHistory notifyHistory = notifyRepository.findByProductId(productId);

        // select status from ProductNotificationHistory where productId = 1;
        notifyHistory.getStatus();

        // select

        return ResponseEntity.ok("잘 생성 되었습니다.");
    }
}

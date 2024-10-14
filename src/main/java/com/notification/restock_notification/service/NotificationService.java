package com.notification.restock_notification.service;

import com.notification.restock_notification.domain.*;
import com.notification.restock_notification.repository.ProductNotificationHistoryRepository;
import com.notification.restock_notification.repository.ProductRepository;
import com.notification.restock_notification.repository.ProductUserNotificationHistoryRepoistory;
import com.notification.restock_notification.repository.ProductUserNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static int MSG_ALERT_CNT = 500;
    private final ProductUserNotificationRepository userNotifyRepository;
    private final ProductNotificationHistoryRepository productHistoryRepository;
    private final ProductRepository productRepository;
    private final ProductUserNotificationHistoryRepoistory userHistoryRepository;

    /*
- IN_PROGRESS (발송 중)
- CANCELED_BY_SOLD_OUT (품절에 의한 발송 중단)
- CANCELED_BY_ERROR (예외에 의한 발송 중단)
- COMPLETED (완료)
*/

    // 재입고 알림 전송 API
    public ResponseEntity<?> sendNotify(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 없음"));

        // 재입고 시 1~10 의 랜덤 재고 추가
        product.addStock();

        // 재입고 알림 정보 저장 및 IN_PROGRESS로 시작
        ProductNotificationHistory notifyHistory = new ProductNotificationHistory(
                product, Status.IN_PROGRESS, getNextRestockVer(productId)
        );
        productHistoryRepository.save(notifyHistory);

        List<ProductUserNotification> userNotifys = userNotifyRepository
                .findAllByHasSubscribedAndProduct_IdOrderByCreatedAtDesc(true, productId);

        List<ProductUserNotificationHistory> userHistories = new ArrayList<>();

        try {
            for (ProductUserNotification userNotify : userNotifys) {
                if (product.getStock() == 0) {
                    // 재고가 소진되면 중단하고 상태를 CANCELED_BY_SOLD_OUT로 변경
                    notifyHistory.setStatus(Status.CANCELED_BY_SOLD_OUT);
                    productHistoryRepository.save(notifyHistory);

                    // 남은 유저들의 상태를 PENDING으로 기록
                    userHistories.add(new ProductUserNotificationHistory(
                            userNotify.getUserId(), notifyHistory, UserStatus.PENDING
                    ));
                    break;
                }

                // 재고가 남아 있을 때 알림 전송 성공
                userHistories.add(new ProductUserNotificationHistory(
                        userNotify.getUserId(), notifyHistory, UserStatus.SUCCESS
                ));
            }

            // 모든 알림이 성공적으로 완료된 경우 COMPLETED로 상태 변경
            if (product.getStock() > 0) {
                notifyHistory.setStatus(Status.COMPLETED);
            }

        } catch (Exception e) {
            // 예외 발생 시 CANCELED_BY_ERROR로 상태 변경 및 남은 유저 PENDING 처리
            notifyHistory.setStatus(Status.CANCELED_BY_ERROR);
            for (ProductUserNotification userNotify : userNotifys) {
                userHistories.add(new ProductUserNotificationHistory(
                        userNotify.getUserId(), notifyHistory, UserStatus.PENDING
                ));
            }
        }

        // 모든 유저 알림 이력 저장
        userHistoryRepository.saveAll(userHistories);
        productHistoryRepository.save(notifyHistory);

        return ResponseEntity.ok("알림 전송이 완료되었습니다.");
    }



    // admin 재입고 manual 전송
    public ResponseEntity<?> retryNotifications(Long productId) {
        return ResponseEntity.ok("잘 생성 되었습니다.");
    }

    private int getNextRestockVer(Long productId) {
        // 1. 해당 상품의 가장 최근 회차 조회
        Optional<Integer> latestRestockVer = productHistoryRepository.findLatestRestockVerByProductId(productId);

        // 2. 알림 이력이 없으면 회차를 1로 시작, 있으면 +1
        return latestRestockVer.orElse(0) + 1;
    }
}

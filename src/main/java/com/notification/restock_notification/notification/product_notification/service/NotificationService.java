package com.notification.restock_notification.notification.product_notification.service;

import com.notification.restock_notification.admin.domain.*;
import com.notification.restock_notification.domain.*;
import com.notification.restock_notification.notification.product_notification.entity.Status;
import com.notification.restock_notification.product.entity.Product;
import com.notification.restock_notification.notification.product_notification.entity.ProductNotificationHistory;
import com.notification.restock_notification.notification.product_notification.repository.ProductNotificationHistoryRepository;
import com.notification.restock_notification.notification.product_user_notification.entity.ProductUserNotification;
import com.notification.restock_notification.notification.product_user_notification.entity.ProductUserNotificationHistory;
import com.notification.restock_notification.product.repository.ProductRepository;
import com.notification.restock_notification.notification.product_user_notification.repository.ProductUserNotificationHistoryRepoistory;
import com.notification.restock_notification.notification.product_user_notification.repository.ProductUserNotificationRepository;
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
        // 2. 재입고 회차 증가 및 저장
        product.increaseRestockVer();
        productRepository.save(product);

        // 재입고 알림 정보 저장 및 IN_PROGRESS로 시작
        ProductNotificationHistory productStatus = new ProductNotificationHistory(
                // 상품 1, reStock +1
                product, Status.IN_PROGRESS, getNextRestockVer(productId)
        );
        productHistoryRepository.save(productStatus);

        // 특정 상품 재입고 알림 신청한 유저 날짜 순으로 저장
        List<ProductUserNotification> userSubscribe = userNotifyRepository
                .findAllByHasSubscribedAndProduct_IdOrderByCreatedAtDesc(true, productId);

        // 특정 상품 회차별 신청한 유저별로 저장용
        List<ProductUserNotificationHistory> userHistories = new ArrayList<>();

        // 알림 설정한 유저 알림 보내기
        try {
            boolean soldOut = false; // 재고 소진 여부 플래그

            for (ProductUserNotification userNotify : userSubscribe) {
                if (product.getStock() == 0 && !soldOut) {
                    // 재고가 소진된 첫 순간에만 상태를 CANCELED_BY_SOLD_OUT로 변경
                    productStatus.changeStatus(Status.CANCELED_BY_SOLD_OUT);
                    productHistoryRepository.save(productStatus);
                    soldOut = true;  // 재고 소진 플래그 설정
                }

                if (soldOut) {
                    // 재고 소진 후 모든 유저는 PENDING 처리
                    userHistories.add(new ProductUserNotificationHistory(
                            userNotify.getUserId(), productStatus, UserStatus.PENDING
                    ));
                } else {
                    // 재고가 남아 있을 때는 알림 전송 성공 처리
                    userHistories.add(new ProductUserNotificationHistory(
                            userNotify.getUserId(), productStatus, UserStatus.SUCCESS
                    ));
                }
            }

            // 모든 알림이 정상적으로 처리된 경우 COMPLETED 상태로 변경
            if (product.getStock() > 0) {
                productStatus.changeStatus(Status.COMPLETED);
            }

        } catch (Exception e) {
            // 예외 발생 시 CANCELED_BY_ERROR로 상태 변경 및 남은 유저 PENDING 처리
            productStatus.changeStatus(Status.CANCELED_BY_ERROR);
            for (ProductUserNotification userNotify : userSubscribe) {
                userHistories.add(new ProductUserNotificationHistory(
                        userNotify.getUserId(), productStatus, UserStatus.PENDING
                ));
            }
        }

        // 모든 유저 알림 이력 저장
        userHistoryRepository.saveAll(userHistories);
        productHistoryRepository.save(productStatus);

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

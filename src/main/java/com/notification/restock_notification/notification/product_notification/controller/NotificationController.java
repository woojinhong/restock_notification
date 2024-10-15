package com.notification.restock_notification.notification.product_notification.controller;

import com.notification.restock_notification.notification.product_notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    // 재입고 알림 전송 API
    @PostMapping("/{productId}/notifications/re-stock")
    public ResponseEntity<?> sendNotify(@PathVariable Long productId){
        return service.sendNotify(productId);
    }

    // 재입고 알림 전송 API (manual)
}

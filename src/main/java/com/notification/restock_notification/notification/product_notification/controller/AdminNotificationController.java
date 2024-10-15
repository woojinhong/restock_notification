package com.notification.restock_notification.notification.product_notification.controller;


import com.notification.restock_notification.notification.product_notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminNotificationController {

    private final NotificationService service;

    @PostMapping("/products/{productId}/notifications/re-stock")
    public ResponseEntity<?> retryNotification(@PathVariable Long productId) {
        service.retryNotifications(productId);
        return ResponseEntity.ok().build();
    }
}

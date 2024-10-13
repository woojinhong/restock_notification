package com.notification.restock_notification.controller;

import com.notification.restock_notification.domain.Product;
import com.notification.restock_notification.domain.ProductUserNotification;
import com.notification.restock_notification.service.ProductUserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductUserNotificationController {

    private final ProductUserNotificationService service;

    @PostMapping("/products/{productId}/notifications/re-stock")
    public ResponseEntity<?> sendNotify(@PathVariable Long productId){
        return service.sendNotify(productId);
    }
}

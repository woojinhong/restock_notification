package com.notification.restock_notification.repository;

import com.notification.restock_notification.product.entity.Product;
import com.notification.restock_notification.notification.product_user_notification.entity.ProductUserNotification;
import com.notification.restock_notification.notification.product_user_notification.repository.ProductUserNotificationRepository;
import com.notification.restock_notification.notification.product_notification.service.NotificationService;
import com.notification.restock_notification.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional  // 테스트 후 데이터 롤백을 보장
class ProductUserNotificationRepositoryTest {

    @Autowired
    private ProductUserNotificationRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private NotificationService notificationService;

    @BeforeEach
    @DisplayName("상품 MOCK DATA 초기화")
    public void addProducts() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(new Product(0));  // 재고 0인 상품 10개 생성
        }
        productRepository.saveAll(products);  // 배치 저장
    }

    @Test
    @DisplayName("유저 알림 신청 MOCK DATA 생성")
    public void addUserNotifications() {
        List<Product> allProducts = productRepository.findAll();
        List<ProductUserNotification> allUserNotifications = new ArrayList<>();

        for (long i = 1; i <= 10000; i++) {
            // 랜덤 상품 선택 (범위 조정)
            Product product = allProducts.get((int) (Math.random() * allProducts.size()));
            allUserNotifications.add(new ProductUserNotification(i, product, 0, true));
        }

        userRepository.saveAll(allUserNotifications);  // 배치 저장

        // 검증: 10,000개의 알림이 정상적으로 저장되었는지 확인
        assertThat(userRepository.findAll().size()).isEqualTo(10000);
    }

    @Test
    @DisplayName("알림 전송 테스트")
    public void testSendNotification() {
        // 1. Mock 데이터 생성
        Product product = productRepository.findAll().get(0);  // 첫 번째 상품 선택

        // 2. 알림 전송 실행
        notificationService.sendNotify(product.getId());

        // 3. 검증: 상품 재고가 0 이상인지 확인
        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertThat(updatedProduct.getStock()).isGreaterThan(0);
    }
}

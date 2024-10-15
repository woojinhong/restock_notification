package com.notification.restock_notification.product.repository;

import com.notification.restock_notification.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}

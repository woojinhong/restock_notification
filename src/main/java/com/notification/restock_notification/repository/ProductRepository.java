package com.notification.restock_notification.repository;

import com.notification.restock_notification.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}

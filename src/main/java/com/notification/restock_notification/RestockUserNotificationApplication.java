package com.notification.restock_notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RestockUserNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestockUserNotificationApplication.class, args);
    }

}

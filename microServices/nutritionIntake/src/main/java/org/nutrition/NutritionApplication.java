package org.nutrition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class NutritionApplication {

    public static void main(String[] args) {
        SpringApplication.run(NutritionApplication.class , args);
    }


}
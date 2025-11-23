package com.edurise.EduRise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.edurise.EduRise")
@EnableJpaRepositories(basePackages = "com.edurise.EduRise.repository")
@EntityScan(basePackages = "com.edurise.EduRise.model")
public class EduRiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduRiseApplication.class, args);
    }
}

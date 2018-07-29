package com.xiao.toggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class FeatureToggleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeatureToggleApplication.class, args);
    }
}

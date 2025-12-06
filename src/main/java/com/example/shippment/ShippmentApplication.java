package com.example.shippment; // This is where the file is located

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan; // 1. IMPORT THIS LINE

@SpringBootApplication
public class ShippmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShippmentApplication.class, args);
    }
}
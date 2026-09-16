package com.dispute;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DisputeServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(DisputeServiceApplication.class, args);
    }
}
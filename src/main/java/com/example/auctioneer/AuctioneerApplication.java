package com.example.auctioneer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuctioneerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctioneerApplication.class, args);
    }

}

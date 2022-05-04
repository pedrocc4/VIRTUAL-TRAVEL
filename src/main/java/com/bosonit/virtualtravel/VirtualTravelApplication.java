package com.bosonit.virtualtravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class VirtualTravelApplication {
    public static void main(String[] args) {
        SpringApplication.run(VirtualTravelApplication.class, args);
    }

}

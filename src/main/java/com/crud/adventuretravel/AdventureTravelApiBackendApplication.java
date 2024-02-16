package com.crud.adventuretravel;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AdventureTravelApiBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(AdventureTravelApiBackendApplication.class, args);
    }
}

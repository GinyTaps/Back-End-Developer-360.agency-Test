package com.agency.listingcar;

import com.agency.listingcar.dto.ListingDto;
import com.agency.listingcar.entities.Dealer;
import com.agency.listingcar.service.ListingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class ListingCarApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListingCarApplication.class, args);
    }

    /*@Bean
    CommandLineRunner start(ListingService listingService) {
        return args -> {
            listingService.createListing(new ListingDto(null, UUID.fromString("e5176df2331446468614910b9bb7b8e9"), "Lexus", 15000000.0));
            listingService.createListing(new ListingDto(null, UUID.fromString("e5176df2331446468614910b9bb7b8e9"), "Audi", 18000000.0));
        };
    }*/

}

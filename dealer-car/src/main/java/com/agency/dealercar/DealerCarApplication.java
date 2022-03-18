package com.agency.dealercar;

import com.agency.dealercar.dto.DealerDto;
import com.agency.dealercar.service.DealerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DealerCarApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealerCarApplication.class, args);
    }

    @Bean
    CommandLineRunner start(DealerService dealerService) {
        return args -> {
            dealerService.createDealer(new DealerDto(null, "Wendy"));
            dealerService.createDealer(new DealerDto(null, "Ela"));
        };
    }

}

package com.agency.listingcar.openFeign;

import com.agency.listingcar.entities.Dealer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "DEALER-CAR", configuration = FeignClientConfiguration.class)
public interface DealerRestClient {
    @GetMapping("/api/dealers/{id}")
    Dealer getDealer(@PathVariable UUID id);
}

package com.agency.listingcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor @Data @NoArgsConstructor
public class ListingRequestDto {
    private UUID id;
    private String vehicle;
    private BigDecimal price;
}

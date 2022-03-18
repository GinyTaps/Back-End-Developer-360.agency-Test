package com.agency.listingcar.dto;

import com.agency.listingcar.entities.Dealer;
import com.agency.listingcar.enums.ListingState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
public class ListingResponseDto {
    private UUID id;
    private String vehicle;
    private BigDecimal price;
    private Dealer dealer;
    private Date createdDate;
    private ListingState state;
}

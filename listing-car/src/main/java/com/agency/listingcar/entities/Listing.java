package com.agency.listingcar.entities;

import com.agency.listingcar.enums.ListingState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor @Data @NoArgsConstructor
public class Listing {
    @Id
    private UUID id;
    private UUID dealerId;
    private String vehicle;
    private BigDecimal price;
    private Date createdDate;
    private ListingState state;
    @Transient
    private Dealer dealer;
}

package com.agency.listingcar.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor @Data @NoArgsConstructor
public class Dealer {
    private UUID id;
    private String name;
}

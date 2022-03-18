package com.agency.dealercar.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor @Data @NoArgsConstructor
public class Dealer {
    @Id
    private UUID id;
    private String name;
}

package com.agency.dealercar.repositories;

import com.agency.dealercar.entities.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DealerRepopsitory extends JpaRepository<Dealer, UUID> {
}

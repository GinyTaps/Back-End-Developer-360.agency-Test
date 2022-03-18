package com.agency.listingcar.repositories;

import com.agency.listingcar.entities.Listing;
import com.agency.listingcar.enums.ListingState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, UUID> {
    List<Listing> findByDealerIdAndStateIsLike(UUID dealerId, ListingState state);
    Integer countAllByDealerIdAndStateIsLike(UUID dealerId, ListingState state);
    Listing findByIdAndStateIs(UUID id, ListingState state);
    Listing findByDealerIdAndStateIsOrderByCreatedDateAsc(UUID dealerId, ListingState state);
    @Query("FROM Listing l  WHERE l.createdDate IN " +
            "(SELECT  MIN(li.createdDate) FROM Listing li WHERE li.dealerId LIKE :x AND li.state LIKE :y)")
    Listing findByDealerIdAndStateAndOldCreatedDate(@Param("x") UUID dealerId, @Param("y") ListingState state);

}

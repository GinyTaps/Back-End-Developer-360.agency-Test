package com.agency.listingcar.web.rest;

import com.agency.listingcar.dto.ListingDto;
import com.agency.listingcar.dto.ListingResponseDto;
import com.agency.listingcar.entities.Listing;
import com.agency.listingcar.service.ListingService;
import com.agency.listingcar.web.rest.util.HeaderUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ListingRestController {
    private ListingService listingService;
    private static final String ENTITY_NAME = "Listing";
    private  final Logger log = LoggerFactory.getLogger(ListingRestController.class);

    @PostMapping("/listings")
    public ResponseEntity<ListingDto> createListing(@RequestBody ListingDto listingDto) {
        log.debug("REST request to create a Listing : {}", listingDto);
        ListingDto listingDto1 = listingService.createListing(listingDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, listingDto1.getVehicle())).body(listingDto1);
    }

    @PutMapping("/listings")
    public ResponseEntity<ListingDto> updateListing(@RequestBody ListingDto listingDto) {
        log.debug("REST request to update a Listing : {}", listingDto);
        ListingDto listingDto1 = listingService.updateListing(listingDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, listingDto1.getVehicle())).body(listingDto);
    }

    @GetMapping("/listingsByDealerAndDraftedState/{dealerId}")
    public ResponseEntity<List<ListingResponseDto>> getAllByDealerAndDState(@PathVariable UUID dealerId) {
        log.debug("REST request to get all Listings of a dealer and drafted state : {}", dealerId);
        List<ListingResponseDto> listingDtos = listingService.getAllListingByDealerIdAndDraftedState(dealerId);
        return ResponseEntity.ok(listingDtos);
    }

    @GetMapping("/listingsByDealerAndPublishedState/{dealerId}")
    public ResponseEntity<List<ListingResponseDto>> getAllByDealerAndPState(@PathVariable UUID dealerId) {
        log.debug("REST request to get all Listings of a dealer and published state : {}", dealerId);
        List<ListingResponseDto> listingDtos = listingService.getAllListingByDealerIdAndPublishedState(dealerId);
        return ResponseEntity.ok(listingDtos);
    }


    @GetMapping("/listings/{id}")
    public ResponseEntity<ListingResponseDto> getListing(@PathVariable UUID id) {
        log.debug("REST request to get a listing of by id : {}", id);
        ListingResponseDto listingDto = listingService.getListing(id);
        return ResponseEntity.ok(listingDto);
    }

    @GetMapping("/listings")
    public ResponseEntity<List<ListingResponseDto>> allListings() {
        log.debug("REST request to get all Listings : {}" );
        List<ListingResponseDto> listingDtos = listingService.getAllListings();
        return ResponseEntity.ok(listingDtos);
    }

    @PutMapping("/publishListing")
    public ResponseEntity<String> publishListing(@RequestBody UUID id) {
        log.debug("REST request to publish a Listing : {}", id);
        String message = listingService.publishListing(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityPublishedAlert(ENTITY_NAME, id.toString())).body(message);
    }

    @PutMapping("/unPublishListing")
    public ResponseEntity<String> unPublishListing(@RequestBody UUID id) {
        log.debug("REST request to unpublish a Listing : {}", id);
        String message = listingService.unPublishListing(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUnpublishedAlert(ENTITY_NAME, id.toString())).body(message);
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        log.debug("REST request to show exception : {}", e);
        return e.getMessage();
    }
}

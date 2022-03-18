package com.agency.listingcar.service;

import com.agency.listingcar.dto.ListingDto;
import com.agency.listingcar.dto.ListingRequestDto;
import com.agency.listingcar.dto.ListingResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ListingService {
    ListingDto createListing(ListingDto listingDto);
    ListingDto updateListing(ListingDto ListingDto);
    ListingResponseDto getListing(UUID id);
    List<ListingResponseDto> getAllListings();
    List<ListingResponseDto> getAllListingByDealerIdAndDraftedState(UUID dealerId);
    List<ListingResponseDto> getAllListingByDealerIdAndPublishedState(UUID dealerId);
    String publishListing(UUID id);
    String unPublishListing(UUID id);
}

package com.agency.listingcar.mappers;

import com.agency.listingcar.dto.ListingDto;
import com.agency.listingcar.dto.ListingRequestDto;
import com.agency.listingcar.dto.ListingResponseDto;
import com.agency.listingcar.entities.Listing;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListingMapper {
    ListingDto  listingToListingDto(Listing listing);
    Listing listingDtoToListing(ListingDto listingDto);
    Listing listingRequestDtoToListing(ListingRequestDto requestDto);
    ListingResponseDto listingToListingResponseDto(Listing listing);

}

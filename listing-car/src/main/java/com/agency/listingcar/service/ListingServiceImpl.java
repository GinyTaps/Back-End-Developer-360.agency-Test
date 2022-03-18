package com.agency.listingcar.service;

import com.agency.listingcar.dto.ListingResponseDto;
import com.agency.listingcar.exceptions.DealerNotFoundException;
import com.agency.listingcar.exceptions.ListingAlreadyPublishedException;
import com.agency.listingcar.exceptions.PublishingLimitReachedException;
import com.agency.listingcar.repositories.ListingRepository;
import com.agency.listingcar.dto.ListingDto;
import com.agency.listingcar.entities.Dealer;
import com.agency.listingcar.entities.Listing;
import com.agency.listingcar.enums.ListingState;
import com.agency.listingcar.mappers.ListingMapper;
import com.agency.listingcar.openFeign.DealerRestClient;
import com.agency.listingcar.web.rest.util.Constants;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ListingServiceImpl implements ListingService {
    private ListingRepository listingRepository;
    private ListingMapper listingMapper;
    private DealerRestClient dealerRestClient;
    private  final Logger log = LoggerFactory.getLogger(ListingService.class);

    @Override
    public ListingDto createListing(ListingDto listingDto) {
        Dealer dealer = dealerRestClient.getDealer(listingDto.getDealerId());
        if(dealer == null) throw new DealerNotFoundException("Dealer not found");
        Listing listing = listingMapper.listingDtoToListing(listingDto);
        listing.setId(UUID.randomUUID());
        listing.setCreatedDate(new Date());
        listing.setState(ListingState.DRAFT);
        log.debug("Creating information", listingDto.getVehicle());
        return listingMapper.listingToListingDto(listingRepository.save(listing));
    }

    @Override
    public ListingDto updateListing(ListingDto listingDto) {
        Listing listing = listingRepository.findById(listingMapper.listingDtoToListing(listingDto).getId()).get();
        if (listing != null) {
            Listing listingToUpdated = listingMapper.listingDtoToListing(listingDto);
            listingToUpdated.setCreatedDate(listing.getCreatedDate());
            listingToUpdated.setState(listing.getState());
            listingMapper.listingToListingDto(listingRepository.save(listingToUpdated));
        }
        else {
            createListing(listingDto);
        }
        log.debug("Updating information", listingDto.getVehicle());
        return listingDto;
    }

    @Override
    public ListingResponseDto getListing(UUID id) {
        Listing listing = listingRepository.findById(id).get();
        Dealer dealer = dealerRestClient.getDealer(listing.getDealerId());
        listing.setDealer(dealer);
        log.debug("Listing information", listing.getVehicle());
        return listingMapper.listingToListingResponseDto(listing);
    }

    @Override
    public List<ListingResponseDto> getAllListings() {
        List<Listing> listings = listingRepository.findAll();
        for (Listing listing: listings) {
            Dealer dealer = dealerRestClient.getDealer(listing.getDealerId());
            listing.setDealer(dealer);
        }
        return listings.stream()
                .map(listing -> listingMapper.listingToListingResponseDto(listing))
                .collect(Collectors.toList());
    }

    @Override
    public List<ListingResponseDto> getAllListingByDealerIdAndDraftedState(UUID dealerId) {
        List<Listing> listings = listingRepository.findByDealerIdAndStateIsLike(dealerId, ListingState.DRAFT);
        for (Listing listing: listings) {
            Dealer dealer = dealerRestClient.getDealer(listing.getDealerId());
            listing.setDealer(dealer);
        }
        return listings
                .stream()
                .map(listing -> listingMapper.listingToListingResponseDto(listing))
                .collect(Collectors.toList());
    }

    @Override
    public List<ListingResponseDto> getAllListingByDealerIdAndPublishedState(UUID dealerId) {
        List<Listing> listings = listingRepository.findByDealerIdAndStateIsLike(dealerId, ListingState.PUBLISHED);
        for (Listing listing: listings) {
            Dealer dealer = dealerRestClient.getDealer(listing.getDealerId());
            listing.setDealer(dealer);
        }
        return listings
                .stream()
                .map(listing -> listingMapper.listingToListingResponseDto(listing))
                .collect(Collectors.toList());
    }

    @Override
    public String publishListing(UUID id) {
        Listing listing = listingRepository.findById(id).get();
        int recordNumber = listingRepository.countAllByDealerIdAndStateIsLike(listing.getDealerId(), ListingState.PUBLISHED).intValue();
        if ( listing!= null && listingRepository.findByIdAndStateIs(id, ListingState.PUBLISHED) != null) throw new ListingAlreadyPublishedException(Constants.LISTING_ALWAYS_PUBLISHED_MSG);
        else if ( recordNumber >= Constants.TIER_LIMIT ) {
            Listing listingOld = listingRepository.findByDealerIdAndStateAndOldCreatedDate(listing.getDealerId(), ListingState.PUBLISHED);
            if ( listingOld!= null) {
                listingOld.setState(ListingState.DRAFT);
                listing.setState(ListingState.PUBLISHED);
                listingRepository.saveAll(Arrays.asList(listingOld, listing));
                return Constants.LIMIT_MESSAGE_OPTION;
            }
        }
        else if ( listing!= null) {
            listing.setState(ListingState.PUBLISHED);
            listingRepository.save(listing);
        }
        log.debug("Publishing information", listing.getVehicle());
        return listing.getVehicle()+Constants.PUBLISHED_MESSAGE;

    }

    @Override
    public String unPublishListing(UUID id) {
        Listing listing = listingRepository.findById(id).get();
        if ( listing!= null) {
            listing.setState(ListingState.DRAFT);
            listingRepository.save(listing);
        }
        log.debug("Unpublishing information", listing.getVehicle());
        return listing.getVehicle()+Constants.UNPUBLISHED_MESSAGE;
    }
}

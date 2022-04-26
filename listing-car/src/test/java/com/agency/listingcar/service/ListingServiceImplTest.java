package com.agency.listingcar.service;

import com.agency.listingcar.dto.ListingDto;
import com.agency.listingcar.dto.ListingResponseDto;
import com.agency.listingcar.entities.Dealer;
import com.agency.listingcar.entities.Listing;
import com.agency.listingcar.enums.ListingState;
import com.agency.listingcar.mappers.ListingMapper;
import com.agency.listingcar.openFeign.DealerRestClient;
import com.agency.listingcar.repositories.ListingRepository;
import com.agency.listingcar.web.rest.util.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;


@ExtendWith(MockitoExtension.class)
public class ListingServiceImplTest {

    @Mock
    private ListingRepository listingRepository;
    @Mock
    private ListingMapper listingMapper;
    @Mock
    private DealerRestClient dealerRestClient;

    @Captor
    private ArgumentCaptor<Listing> listingArgumentCaptor;

    @Test
    void createListing() {
        ListingServiceImpl listingService = new ListingServiceImpl(listingRepository, listingMapper, dealerRestClient);
        Dealer dealer = new Dealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");
        Mockito.when(dealerRestClient.getDealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"))).thenReturn(dealer);

        Listing listing = new Listing(null, dealer.getId(), "Lexus", BigDecimal.valueOf(15000000.0), new Date(), ListingState.DRAFT, dealer);
        ListingDto listingDto = new ListingDto(null, dealer.getId(), "Lexus", BigDecimal.valueOf(15000000.0));

        // Mockito.when(listingRepository.findById(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"))).thenReturn(Optional.of(listing));
        Mockito.when(listingMapper.listingDtoToListing(listingDto)).thenReturn(listing);

        listingService.createListing(listingDto);

        Mockito.verify(listingRepository, Mockito.times(1)).save(listingArgumentCaptor.capture());
        // Assertions.assertThat(listingArgumentCaptor.getValue().getId()).isEqualTo(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"));
        Assertions.assertThat(listingArgumentCaptor.getValue().getDealerId()).isEqualTo(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"));
        Assertions.assertThat(listingArgumentCaptor.getValue().getVehicle()).isEqualTo("Lexus");
        Assertions.assertThat(listingArgumentCaptor.getValue().getId()).isEqualTo(listing.getId());
    }

    @Test
    void updateListing() {
        ListingServiceImpl listingService = new ListingServiceImpl(listingRepository, listingMapper, dealerRestClient);
        Dealer dealer = new Dealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");

        Listing listing = new Listing(null, dealer.getId(), "Lexus", BigDecimal.valueOf(15000000.0), new Date(), ListingState.DRAFT, dealer);
        Mockito.when(listingRepository.findById(listing.getId())).thenReturn(Optional.of(listing));
        ListingDto listingDto = new ListingDto(null, dealer.getId(), "Audi Q2", BigDecimal.valueOf(70000000.0));

        Mockito.when(listingMapper.listingDtoToListing(listingDto)).thenReturn(listing);

        ListingDto listingResponseDto = listingService.updateListing(listingDto);

        Assertions.assertThat(listingResponseDto.getDealerId()).isEqualTo(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"));
        Assertions.assertThat(listingResponseDto.getVehicle()).isEqualTo("Audi Q2");
        Assertions.assertThat(listingResponseDto.getId()).isEqualTo(listing.getId());
    }

    @Test
    void getListing() {
        ListingServiceImpl listingService = new ListingServiceImpl(listingRepository, listingMapper, dealerRestClient);
        Dealer dealer = new Dealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");
        Mockito.when(dealerRestClient.getDealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"))).thenReturn(dealer);

        Listing listing = new Listing(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"), dealer.getId(), "Lexus", BigDecimal.valueOf(15000000.0), new Date(), ListingState.DRAFT, dealer);
        ListingResponseDto listingResponseDto = new ListingResponseDto(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"), "Lexus", BigDecimal.valueOf(15000000.0), dealer, new Date(), ListingState.DRAFT);

        Mockito.when(listingRepository.findById(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"))).thenReturn(Optional.of(listing));
        Mockito.when(listingMapper.listingToListingResponseDto(Mockito.any(Listing.class))).thenReturn(listingResponseDto);

        ListingResponseDto getResponse = listingService.getListing(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"));

        Assertions.assertThat(getResponse.getId()).isEqualTo(listingResponseDto.getId());
        Assertions.assertThat(getResponse.getDealer().getId()).isEqualTo(listingResponseDto.getDealer().getId());
        Assertions.assertThat(getResponse.getVehicle()).isEqualTo(listingResponseDto.getVehicle());
    }

    @Test
    void getAllListings() {
        ListingServiceImpl listingService = new ListingServiceImpl(listingRepository, listingMapper, dealerRestClient);
        Dealer dealer = new Dealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");
        Mockito.when(dealerRestClient.getDealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"))).thenReturn(dealer);

        Listing listing = new Listing(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"), dealer.getId(), "Lexus", BigDecimal.valueOf(15000000.0), new Date(), ListingState.DRAFT, dealer);
        Listing listing1 = new Listing(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e8"), dealer.getId(), "Range rover evoque", BigDecimal.valueOf(55000000.0), new Date(), ListingState.DRAFT, dealer);
        List<Listing> listings = new ArrayList<>();
        Collections.addAll(listings, listing, listing1);
        ListingResponseDto listingResponseDto = new ListingResponseDto(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"), "Lexus", BigDecimal.valueOf(15000000.0), dealer, new Date(), ListingState.DRAFT);

        Mockito.when(listingRepository.findAll()).thenReturn(listings);
        Mockito.when(listingMapper.listingToListingResponseDto(Mockito.any(Listing.class))).thenReturn(listingResponseDto);

        List<ListingResponseDto> getResponse = listingService.getAllListings();

        Assertions.assertThat(getResponse.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void getAllListingByDealerIdAndDraftedState() {
        ListingServiceImpl listingService = new ListingServiceImpl(listingRepository, listingMapper, dealerRestClient);
        Dealer dealer = new Dealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");
        Mockito.when(dealerRestClient.getDealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"))).thenReturn(dealer);

        Listing listing = new Listing(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"), dealer.getId(), "Lexus", BigDecimal.valueOf(15000000.0), new Date(), ListingState.DRAFT, dealer);
        List<Listing> listings = new ArrayList<>();
        listings.add(listing);
        ListingResponseDto listingResponseDto = new ListingResponseDto(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"), "Lexus", BigDecimal.valueOf(15000000.0), dealer, new Date(), ListingState.DRAFT);

        Mockito.when(listingRepository.findByDealerIdAndStateIsLike(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), ListingState.DRAFT)).thenReturn(listings);
        Mockito.when(listingMapper.listingToListingResponseDto(Mockito.any(Listing.class))).thenReturn(listingResponseDto);

        List<ListingResponseDto> getResponse = listingService.getAllListingByDealerIdAndDraftedState(listingResponseDto.getDealer().getId());

        Assertions.assertThat(getResponse.size()).isNotNull();
    }

    @Test
    void getAllListingByDealerIdAndPublishedState() {
        ListingServiceImpl listingService = new ListingServiceImpl(listingRepository, listingMapper, dealerRestClient);
        Dealer dealer = new Dealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");
        Mockito.when(dealerRestClient.getDealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"))).thenReturn(dealer);

        Listing listing = new Listing(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"), dealer.getId(), "BMW ix", BigDecimal.valueOf(5000000.0), new Date(), ListingState.PUBLISHED, dealer);
        List<Listing> listings = new ArrayList<>();
        listings.add(listing);
        ListingResponseDto listingResponseDto = new ListingResponseDto(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"), "BMW ix", BigDecimal.valueOf(5000000.0), dealer, new Date(), ListingState.DRAFT);

        Mockito.when(listingRepository.findByDealerIdAndStateIsLike(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), ListingState.PUBLISHED)).thenReturn(listings);
        Mockito.when(listingMapper.listingToListingResponseDto(Mockito.any(Listing.class))).thenReturn(listingResponseDto);

        List<ListingResponseDto> getResponse = listingService.getAllListingByDealerIdAndPublishedState(listingResponseDto.getDealer().getId());

        Assertions.assertThat(getResponse.size()).isEqualTo(1);
    }

    @Test
    void publishListing() {
        ListingServiceImpl listingService = new ListingServiceImpl(listingRepository, listingMapper, dealerRestClient);
        Dealer dealer = new Dealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");

        Listing listing = new Listing(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"), dealer.getId(), "Lexus", BigDecimal.valueOf(15000000.0), new Date(), ListingState.DRAFT, dealer);
        ListingResponseDto listingResponseDto = new ListingResponseDto(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"), "Lexus", BigDecimal.valueOf(15000000.0), dealer, new Date(), ListingState.DRAFT);

        Mockito.when(listingRepository.findById(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"))).thenReturn(Optional.of(listing));

        String getResponse = listingService.publishListing(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"));

        Assertions.assertThat(getResponse).isEqualTo("Lexus"+Constants.PUBLISHED_MESSAGE);
    }

    @Test
    void unPublishListing() {
        ListingServiceImpl listingService = new ListingServiceImpl(listingRepository, listingMapper, dealerRestClient);
        Dealer dealer = new Dealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");

        Listing listing = new Listing(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"), dealer.getId(), "Lexus", BigDecimal.valueOf(15000000.0), new Date(), ListingState.DRAFT, dealer);
        ListingResponseDto listingResponseDto = new ListingResponseDto(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"), "Lexus", BigDecimal.valueOf(15000000.0), dealer, new Date(), ListingState.DRAFT);

        Mockito.when(listingRepository.findById(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"))).thenReturn(Optional.of(listing));

        String getResponse = listingService.unPublishListing(UUID.fromString("e5176df2-3314-4646-8614-910b9bb7b8e9"));

        Assertions.assertThat(getResponse).isNotBlank();
    }
}
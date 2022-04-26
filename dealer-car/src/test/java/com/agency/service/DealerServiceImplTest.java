package com.agency.dealercar.service;

import com.agency.dealercar.dto.DealerDto;
import com.agency.dealercar.entities.Dealer;
import com.agency.dealercar.mappers.DealerMapper;
import com.agency.dealercar.repositories.DealerRepopsitory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
public class DealerServiceImplTest {

    @Mock
    private DealerRepopsitory dealerRepopsitory;
    @Mock
    private DealerMapper dealerMapper;

    @Captor
    private ArgumentCaptor<Dealer> dealerArgumentCaptor;


    @Test
    @DisplayName("Get dealer by id")
    void getDealer() {
        DealerServiceImpl dealerService = new DealerServiceImpl(dealerRepopsitory, dealerMapper);
        Dealer dealer = new Dealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");
        DealerDto dealerDto = new DealerDto(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");

        Mockito.when(dealerRepopsitory.findById(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"))).thenReturn(Optional.of(dealer));
        Mockito.when(dealerMapper.dealerToDealerDto(Mockito.any(Dealer.class))).thenReturn(dealerDto);

        DealerDto getResponse = dealerService.getDealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"));

        Assertions.assertThat(getResponse.getId()).isEqualTo(dealerDto.getId());
        Assertions.assertThat(getResponse.getName()).isEqualTo(dealerDto.getName());
    }

    @Test
    @DisplayName("Save a dealer")
    void createDealer() {

        DealerServiceImpl dealerService = new DealerServiceImpl(dealerRepopsitory, dealerMapper);
        Dealer dealer = new Dealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");
        DealerDto dealerDto = new DealerDto(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");

        // Mockito.when(dealerRepopsitory.findById(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"))).thenReturn(Optional.of(dealer));
        Mockito.when(dealerMapper.dealerDtoToDealer(dealerDto)).thenReturn(dealer);

        dealerService.createDealer(dealerDto);

        Mockito.verify(dealerRepopsitory, Mockito.times(1)).save(dealerArgumentCaptor.capture());
        Assertions.assertThat(dealerArgumentCaptor.getValue().getId()).isEqualTo(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"));
        Assertions.assertThat(dealerArgumentCaptor.getValue().getName()).isEqualTo("Wendy");
    }

    @Test
    void updateDealer() {
        DealerServiceImpl dealerService = new DealerServiceImpl(dealerRepopsitory, dealerMapper);
        Dealer dealer = new Dealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");
        Mockito.when(dealerRepopsitory.findById(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"))).thenReturn(Optional.of(dealer));
        
        DealerDto dealerDto = new DealerDto(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Ela");
        Mockito.when(dealerMapper.dealerDtoToDealer(dealerDto)).thenReturn(dealer);

        DealerDto dealerResponseDto = dealerService.updateDealer(dealerDto);

        Assertions.assertThat(dealerResponseDto.getId()).isEqualTo(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"));
        Assertions.assertThat(dealerResponseDto.getName()).isEqualTo("Ela");
    }


    @Test
    void getAllDealers() {
        DealerServiceImpl dealerService = new DealerServiceImpl(dealerRepopsitory, dealerMapper);
        Dealer dealer = new Dealer(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");
        // Dealer dealer1 = new Dealer(UUID.fromString("e5176df2331446468614910b9bb7b8e9"), "Ela");
        List<Dealer> dealers = new ArrayList<>();
        dealers.add(dealer);
        DealerDto dealerDto = new DealerDto(UUID.fromString("30e2e517-7ab4-410d-8756-b699cf062c4b"), "Wendy");

        Mockito.when(dealerRepopsitory.findAll()).thenReturn(dealers);
        Mockito.when(dealerMapper.dealerToDealerDto(Mockito.any(Dealer.class))).thenReturn(dealerDto);

        List<DealerDto> getResponse = dealerService.getAllDealers();
        Assertions.assertThat(getResponse).size().isEqualTo(1);
    }
}
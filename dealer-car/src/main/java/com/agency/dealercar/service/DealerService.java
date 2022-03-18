package com.agency.dealercar.service;

import com.agency.dealercar.dto.DealerDto;

import java.util.List;
import java.util.UUID;

public interface DealerService {
    DealerDto createDealer(DealerDto dealerDto);
    DealerDto updateDealer(DealerDto dealerDto);
    DealerDto getDealer(UUID id);
    List<DealerDto> getAllDealers();

}

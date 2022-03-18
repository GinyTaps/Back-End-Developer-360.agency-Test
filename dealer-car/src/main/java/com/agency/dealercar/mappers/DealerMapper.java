package com.agency.dealercar.mappers;

import com.agency.dealercar.dto.DealerDto;
import com.agency.dealercar.entities.Dealer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealerMapper {
    Dealer dealerDtoToDealer(DealerDto dealerDto);
    DealerDto dealerToDealerDto(Dealer dealer);
}

package com.agency.dealercar.service;

import com.agency.dealercar.dto.DealerDto;
import com.agency.dealercar.entities.Dealer;
import com.agency.dealercar.repositories.DealerRepopsitory;
import com.agency.dealercar.mappers.DealerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class DealerServiceImpl implements DealerService {
    private DealerRepopsitory dealerRepopsitory;
    private DealerMapper dealerMapper;
    @Override
    public DealerDto createDealer(DealerDto dealerDto) {
        dealerDto.setId(UUID.randomUUID());
        Dealer dealer = dealerRepopsitory.save(dealerMapper.dealerDtoToDealer(dealerDto));
        return dealerMapper.dealerToDealerDto(dealer);
    }

    @Override
    public DealerDto updateDealer(DealerDto dealerDto) {
        // if(dealerRepopsitory.getById(dealerMapper.dealerDtoToDealer(dealerDto).getId()) != null) {
        if(dealerRepopsitory.findById(dealerMapper.dealerDtoToDealer(dealerDto).getId()).get() != null){
            dealerRepopsitory.save(dealerMapper.dealerDtoToDealer(dealerDto));
        }
        else {
            createDealer(dealerDto);
        }
        return dealerDto;
    }

    @Override
    public DealerDto getDealer(UUID id) {
        return dealerMapper.dealerToDealerDto(dealerRepopsitory.getById(id));
    }

    @Override
    public List<DealerDto> getAllDealers() {
        List<Dealer> dealers = dealerRepopsitory.findAll();
        List<DealerDto> dealerDtos = dealers.stream()
                .map(dealer -> dealerMapper.dealerToDealerDto(dealer))
                .collect(Collectors.toList());
        return dealerDtos;
    }
}

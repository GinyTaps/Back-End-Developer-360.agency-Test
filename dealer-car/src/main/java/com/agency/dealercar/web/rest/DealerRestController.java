package com.agency.dealercar.web.rest;

import com.agency.dealercar.dto.DealerDto;
import com.agency.dealercar.service.DealerService;
import com.agency.dealercar.web.rest.util.HeaderUtil;
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
public class DealerRestController {
    private DealerService dealerService;
    private static final String ENTITY_NAME = "Dealer";
    private  final Logger log = LoggerFactory.getLogger(DealerRestController.class);

    @PostMapping("/dealers")
    ResponseEntity<DealerDto> save(@RequestBody DealerDto dealerDto) {
        log.debug("REST request to create a Dealer : {}", dealerDto);
        DealerDto dealerDto1 = dealerService.createDealer(dealerDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, dealerDto1.getName())).body(dealerDto1);
    }

    @PutMapping("/dealers")
    ResponseEntity<DealerDto> update(@RequestBody DealerDto dealerDto) {
        log.debug("REST request to update a Dealer : {}", dealerDto);
        DealerDto dealerDto1 = dealerService.updateDealer(dealerDto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dealerDto1.getName())).body(dealerDto1);
    }

    /*@PostMapping("/getDealer")
    ResponseEntity<DealerDto> getOneDealer(@RequestBody UUID id) {
        DealerDto dealerDto = dealerService.getDealer(id);
        return ResponseEntity.ok(dealerDto);
    }*/

    @GetMapping("/dealers/{id}")
    ResponseEntity<DealerDto> getDealer(@PathVariable UUID id) {
        log.debug("REST request to get a Dealer : {}", id);
        DealerDto dealerDto = dealerService.getDealer(id);
        return ResponseEntity.ok(dealerDto);
    }

    @GetMapping("/dealers")
    ResponseEntity<List<DealerDto>> allDealers() {
        log.debug("REST request to get all Dealers : {}");
        List<DealerDto> dealerDto = dealerService.getAllDealers();
        return ResponseEntity.ok(dealerDto);
    }
}

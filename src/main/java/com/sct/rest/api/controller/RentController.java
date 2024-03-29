package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.enums.RentStatus;
import com.sct.rest.api.model.filter.RentPageableFilter;
import com.sct.rest.api.security.SecurityContext;
import com.sct.rest.api.service.RentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/rent")
@Validated
@RequiredArgsConstructor
@Slf4j
public class RentController {

    private final RentService rentService;

    @GetMapping("/all")
    public ResponseEntity<List<RentDto>> getAllRent() {
        List<RentDto> rents = rentService.getAllRent();
        log.info("Get all rents count {}", rents.size());
        return ResponseEntity.ok(rents);
    }

    @GetMapping("/current/all")
    public ResponseEntity<List<RentDto>> getAllRentForCurrentUser(@RequestParam RentStatus status) {
        List<RentDto> rents = rentService.getAllRentForCurrentUserByStatus(status);
        log.info("Get all rents for user {} by status {} count: {}",
                SecurityContext.get().getCustomerLogin(), status, rents.size());
        return ResponseEntity.ok(rents);
    }

    @GetMapping(value = "/all/pageable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<RentDto>> getAllRentFilterAndPageable(RentPageableFilter filter) {
        Page<RentDto> page = rentService.getAllRentFilterAndPageable(filter);
        log.info("Get pageable rent: {}", page);
        return ResponseEntity.ok(page);
    }
}

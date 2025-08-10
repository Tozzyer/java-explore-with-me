package ru.practicum.ewm.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dto.NewEndpointHitRequestDto;
import ru.practicum.ewm.dto.EndpointHitResponseDto;
import ru.practicum.ewm.dto.ViewStatsResponseDto;
import ru.practicum.ewm.server.service.EndpointHitService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.support.DateTimeFormatters.DATE_TIME_FORMAT;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EndpointHitController {

    private final EndpointHitService endpointHitService;

    @GetMapping("/stats")
    public List<ViewStatsResponseDto> getStats(
            @RequestParam("start") @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime end,
            @RequestParam(value = "uris", required = false) List<String> uris,
            @RequestParam(value = "unique", required = false, defaultValue = "false") Boolean unique
    ) {
        log.info("GET /stats: start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return endpointHitService.getStat(start, end, uris, unique);
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitResponseDto createEndpointHit(
            @Valid @RequestBody NewEndpointHitRequestDto newEndpointHitDto
    ) {
        log.info("POST /hit: {}", newEndpointHitDto);
        return endpointHitService.createHit(newEndpointHitDto);
    }
}

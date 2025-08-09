package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitRequestDto;
import ru.practicum.dto.EndpointHitResponseDto;
import ru.practicum.dto.ViewStatsResponseDto;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.service.StatsService;

import jakarta.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;
    private final StatsMapper statsMapper;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitResponseDto create(@RequestBody @Valid EndpointHitRequestDto dto) {
        log.info("Received hit: {}", dto);
        return statsMapper.toEntityEndpointHitResponseDto(
                statsService.createHit(statsMapper.toDtoEndpointHit(dto))
        );
    }

    @GetMapping("/stats")
    public List<ViewStatsResponseDto> getStats(@RequestParam Timestamp start,
                                               @RequestParam Timestamp end,
                                               @RequestParam List<String> uris,
                                               @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Requesting stats: start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return statsMapper.toListViewStatsResponseDto(
                statsService.getStats(start, end, uris, unique)
        );
    }
}

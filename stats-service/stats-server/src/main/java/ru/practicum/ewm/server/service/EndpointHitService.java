package ru.practicum.ewm.server.service;

import ru.practicum.ewm.dto.NewEndpointHitRequestDto;
import ru.practicum.ewm.dto.EndpointHitResponseDto;
import ru.practicum.ewm.dto.ViewStatsResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitService {

    List<ViewStatsResponseDto> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

    EndpointHitResponseDto createHit(NewEndpointHitRequestDto newEndpointHitDto);

}
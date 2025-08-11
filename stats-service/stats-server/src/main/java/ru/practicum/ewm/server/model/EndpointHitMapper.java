package ru.practicum.ewm.server.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.dto.NewEndpointHitRequestDto;
import ru.practicum.ewm.dto.EndpointHitResponseDto;

import java.time.LocalDateTime;

import static ru.practicum.ewm.support.DateTimeFormatters.FORMATTER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndpointHitMapper {

    public static EndpointHit toNewEndpointHit(final NewEndpointHitRequestDto dto) {
        return EndpointHit.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(LocalDateTime.parse(dto.getTimestamp(), FORMATTER))
                .build();
    }

    public static EndpointHitResponseDto toEndpointHitResponseDto(final EndpointHit hit) {
        return EndpointHitResponseDto.builder()
                .id(hit.getId())
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp().format(FORMATTER))
                .build();
    }
}

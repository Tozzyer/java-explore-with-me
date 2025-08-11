package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * DTO для ответа при сохранении хита статистики.
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class EndpointHitResponseDto {

    private final Long id;
    private final String app;
    private final String uri;
    private final String ip;
    private final String timestamp;
}

package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.EndpointHitRequestDto;
import ru.practicum.dto.EndpointHitResponseDto;
import ru.practicum.dto.ViewStatsResponseDto;
import ru.practicum.entity.EndpointHit;
import ru.practicum.entity.ViewStats;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatsMapper {

    EndpointHit toDtoEndpointHit(EndpointHitRequestDto dto);

    EndpointHitResponseDto toEntityEndpointHitResponseDto(EndpointHit entity);

    ViewStatsResponseDto toViewStatsResponseDto(ViewStats stats);

    List<ViewStatsResponseDto> toListViewStatsResponseDto(List<ViewStats> statsList);
}

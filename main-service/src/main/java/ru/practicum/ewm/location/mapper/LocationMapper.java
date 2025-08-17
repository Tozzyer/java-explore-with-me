package ru.practicum.ewm.location.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.model.Location;

@UtilityClass
public class LocationMapper {

    public static LocationDto toLocationDto(final Location location) {
        if (location == null) {
            return null;
        }
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}

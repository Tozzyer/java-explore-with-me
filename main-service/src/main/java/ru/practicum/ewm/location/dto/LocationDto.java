package ru.practicum.ewm.location.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class LocationDto {
    Float lat;
    Float lon;
}

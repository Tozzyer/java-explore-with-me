package ru.practicum.ewm.category.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryResponseDto {
    Long id;
    String name;
}

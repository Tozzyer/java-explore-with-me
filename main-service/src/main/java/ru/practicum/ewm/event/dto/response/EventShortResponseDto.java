package ru.practicum.ewm.event.dto.response;

import lombok.Builder;
import lombok.Value;
import ru.practicum.ewm.category.dto.response.CategoryResponseDto;
import ru.practicum.ewm.user.dto.response.UserShortResponseDto;

@Value
@Builder(toBuilder = true)
public class EventShortResponseDto {
    Long id;

    String title;
    String annotation;

    CategoryResponseDto category;
    UserShortResponseDto initiator;

    String eventDate;

    boolean paid;

    Long confirmedRequests;
    Long views;
}

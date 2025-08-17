package ru.practicum.ewm.event.dto.response;

import lombok.Builder;
import lombok.Value;
import ru.practicum.ewm.category.dto.response.CategoryResponseDto;
import ru.practicum.ewm.enums.EventState;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.user.dto.response.UserShortResponseDto;

@Value
@Builder(toBuilder = true)
public class EventFullResponseDto {

    Long id;

    String title;
    String annotation;
    String description;

    CategoryResponseDto category;
    UserShortResponseDto initiator;

    String createdOn;
    String publishedOn;
    String eventDate;

    LocationDto location;

    boolean paid;
    boolean requestModeration;
    Integer participantLimit;

    Long confirmedRequests;
    Long views;

    EventState state;
}

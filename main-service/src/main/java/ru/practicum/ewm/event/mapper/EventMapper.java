package ru.practicum.ewm.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.event.dto.request.NewEventRequestDto;
import ru.practicum.ewm.event.dto.response.EventFullResponseDto;
import ru.practicum.ewm.event.dto.response.EventShortResponseDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.location.mapper.LocationMapper;
import ru.practicum.ewm.user.mapper.UserMapper;

import java.time.LocalDateTime;

import static ru.practicum.ewm.constant.DateTimeFormatters.FORMATTER;

@UtilityClass
public class EventMapper {

    public static Event toNewEvent(final NewEventRequestDto newEventDto) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(LocalDateTime.parse(newEventDto.getEventDate(), FORMATTER))
                .paid(newEventDto.isPaid())
                .requestModeration(newEventDto.isRequestModeration())
                .participantLimit(resolveParticipantLimit(newEventDto.getParticipantLimit()))
                .title(newEventDto.getTitle())
                .build();
    }

    public static EventFullResponseDto toEventFullResponseDto(final Event event) {
        return EventFullResponseDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .category(CategoryMapper.toCategoryResponseDto(event.getCategory()))
                .initiator(UserMapper.toUserShortResponseDto(event.getInitiator()))
                .createdOn(event.getCreatedOn().format(FORMATTER))
                .publishedOn(event.getPublishedOn() != null ? event.getPublishedOn().format(FORMATTER) : null)
                .eventDate(event.getEventDate().format(FORMATTER))
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .paid(event.isPaid())
                .requestModeration(event.isRequestModeration())
                .participantLimit(event.getParticipantLimit())
                .confirmedRequests(resolveConfirmedRequests(event))
                .views(event.getViews())
                .state(event.getState())
                .build();
    }

    public static EventShortResponseDto toEventShortResponseDto(final Event event) {
        return EventShortResponseDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryResponseDto(event.getCategory()))
                .initiator(UserMapper.toUserShortResponseDto(event.getInitiator()))
                .eventDate(event.getEventDate().format(FORMATTER))
                .paid(event.isPaid())
                .confirmedRequests(resolveConfirmedRequests(event))
                .views(event.getViews())
                .build();
    }

    private static int resolveParticipantLimit(final Integer limit) {
        return limit != null ? limit : 0;
    }

    private static long resolveConfirmedRequests(final Event event) {
        return event.getParticipationRequests() != null
                ? event.getParticipationRequests().size()
                : 0L;
    }
}

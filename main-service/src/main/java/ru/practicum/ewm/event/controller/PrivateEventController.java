package ru.practicum.ewm.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.dto.request.NewEventRequestDto;
import ru.practicum.ewm.event.dto.request.UpdateEventUserRequestDto;
import ru.practicum.ewm.event.dto.response.EventFullResponseDto;
import ru.practicum.ewm.event.dto.response.EventShortResponseDto;
import ru.practicum.ewm.event.service.PrivateEventService;
import ru.practicum.ewm.request.dto.request.EventRequestStatusUpdateRequestDto;
import ru.practicum.ewm.request.dto.response.ParticipationRequestResponseDto;
import ru.practicum.ewm.request.dto.response.EventRequestStatusUpdateResultResponseDto;

import java.util.List;

import static ru.practicum.ewm.support.ControllerConstants.DEFAULT_TEN;
import static ru.practicum.ewm.support.ControllerConstants.DEFAULT_ZERO;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class PrivateEventController {

    private final PrivateEventService privateEventService;

    @GetMapping("/{userId}/events")
    public List<EventShortResponseDto> getUserEvents(@PathVariable("userId") @Positive Long userId,
                                                     @RequestParam(defaultValue = DEFAULT_ZERO) @PositiveOrZero int from,
                                                     @RequestParam(defaultValue = DEFAULT_TEN) @Positive int size) {
        Long uId = userId;
        log.info("GET /users/{}/events — список событий пользователя (from={}, size={})", uId, from, size);
        return privateEventService.getUserEvents(uId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullResponseDto getUserEventById(@PathVariable("userId") @Positive Long userId,
                                                 @PathVariable("eventId") @Positive Long eventId) {
        Long uId = userId;
        Long evtId = eventId;
        log.info("GET /users/{}/events/{} — событие пользователя", uId, evtId);
        return privateEventService.getUserEventById(uId, evtId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestResponseDto> getParticipationRequests(@PathVariable("userId") @Positive Long userId,
                                                                          @PathVariable("eventId") @Positive Long eventId) {
        Long uId = userId;
        Long evtId = eventId;
        log.info("GET /users/{}/events/{}/requests — заявки на участие", uId, evtId);
        return privateEventService.getParticipationRequests(uId, evtId);
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullResponseDto createEvent(@Valid @RequestBody NewEventRequestDto newEventDto,
                                            @PathVariable("userId") @Positive Long userId) {
        Long uId = userId;
        log.info("POST /users/{}/events — создание события {}", uId, newEventDto);
        return privateEventService.createEvent(newEventDto, uId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullResponseDto updateUserEvent(@Valid @RequestBody UpdateEventUserRequestDto updateEventUserDto,
                                                @PathVariable("userId") @Positive Long userId,
                                                @PathVariable("eventId") @Positive Long eventId) {
        Long uId = userId;
        Long evtId = eventId;
        log.info("PATCH /users/{}/events/{} — изменение события {}", uId, evtId, updateEventUserDto);
        return privateEventService.updateUserEvent(updateEventUserDto, uId, evtId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResultResponseDto updateParticipationRequestStatuses(
            @Valid @RequestBody EventRequestStatusUpdateRequestDto updateResult,
            @PathVariable("userId") @Positive Long userId,
            @PathVariable("eventId") @Positive Long eventId) {
        Long uId = userId;
        Long evtId = eventId;
        log.info("PATCH /users/{}/events/{}/requests — изменение статусов заявок {}", uId, evtId, updateResult);
        return privateEventService.updateParticipationRequestStatuses(updateResult, uId, evtId);
    }
}

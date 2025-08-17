package ru.practicum.ewm.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.request.NewEndpointHitRequestDto;
import ru.practicum.ewm.event.dto.response.EventFullResponseDto;
import ru.practicum.ewm.event.dto.response.EventShortResponseDto;
import ru.practicum.ewm.event.service.PublicEventService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.constant.DateTimeFormatters.FORMATTER;
import static ru.practicum.ewm.support.ApplicationConstants.APPLICATION_NAME;
import static ru.practicum.ewm.support.ControllerConstants.DEFAULT_TEN;
import static ru.practicum.ewm.support.ControllerConstants.DEFAULT_ZERO;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/events")
public class PublicEventController {

    private final PublicEventService publicEventService;
    private final StatsClient statsClient;

    @GetMapping
    public List<EventShortResponseDto> searchPublicEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false) Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = DEFAULT_ZERO) @PositiveOrZero int from,
            @RequestParam(defaultValue = DEFAULT_TEN) @Positive int size,
            HttpServletRequest request) {

        log.info("GET /events — фильтр: text={}, categories={}, paid={}, rangeStart={}, rangeEnd={}, onlyAvailable={}, sort={}, from={}, size={}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);

        sendStat(request);
        return publicEventService.searchPublicEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullResponseDto getEventById(@PathVariable("eventId") @Positive Long eventId,
                                             HttpServletRequest request) {
        Long evtId = eventId;
        log.info("GET /events/{} — просмотр события", evtId);
        sendStat(request);
        return publicEventService.getEventById(evtId);
    }

    private void sendStat(HttpServletRequest request) {
        try {
            NewEndpointHitRequestDto dto = NewEndpointHitRequestDto.builder()
                    .app(APPLICATION_NAME)
                    .ip(request.getRemoteAddr())
                    .uri(request.getRequestURI())
                    .timestamp(LocalDateTime.now().format(FORMATTER))
                    .build();
            statsClient.saveEvent(dto);
        } catch (Exception ex) {
            log.warn("Не удалось отправить данные статистики: {}", ex.getMessage());
        }
    }
}

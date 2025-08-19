package ru.practicum.ewm.request.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.request.dto.response.ParticipationRequestResponseDto;
import ru.practicum.ewm.request.service.ParticipationRequestService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateParticipationRequestController {

    private final ParticipationRequestService participationRequestService;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestResponseDto> getUserParticipationRequests(@PathVariable @Positive final Long userId) {
        log.info("Получение заявок пользователя id={}", userId);
        return participationRequestService.getUserParticipationRequests(userId);
    }

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestResponseDto createParticipationRequest(@PathVariable @Positive final Long userId,
                                                                      @RequestParam @Positive final Long eventId) {
        log.info("Создание заявки пользователем id={} на участие в событии id={}", userId, eventId);
        return participationRequestService.createParticipationRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestResponseDto cancelParticipationRequest(@PathVariable @Positive final Long userId,
                                                                      @PathVariable @Positive final Long requestId) {
        log.info("Отмена заявки id={} пользователем id={}", requestId, userId);
        return participationRequestService.cancelParticipationRequest(userId, requestId);
    }
}

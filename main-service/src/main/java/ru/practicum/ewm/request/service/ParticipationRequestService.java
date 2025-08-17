package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.dto.response.ParticipationRequestResponseDto;

import java.util.List;

public interface ParticipationRequestService {

    ParticipationRequestResponseDto createParticipationRequest(final Long userId, final Long eventId);

    List<ParticipationRequestResponseDto> getUserParticipationRequests(final Long userId);

    ParticipationRequestResponseDto cancelParticipationRequest(final Long userId, final Long requestId);
}

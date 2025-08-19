package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.enums.EventState;
import ru.practicum.ewm.enums.RequestStatus;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.support.EntityHelper;
import ru.practicum.ewm.request.dto.response.ParticipationRequestResponseDto;
import ru.practicum.ewm.request.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.request.repository.ParticipationRequestRepository;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository participationRequestRepository;
    private final EntityHelper entityHelper;

    @Override
    @Transactional
    public ParticipationRequestResponseDto createParticipationRequest(final Long userId, final Long eventId) {
        log.info("Создание запроса пользователем id={} для события id={}", userId, eventId);

        final Event event = entityHelper.getExistingEventByIdOrThrow(eventId);
        validateParticipationRequest(event, userId);
        final User user = entityHelper.getExistingUserByIdOrThrow(userId);

        final ParticipationRequest newRequest = ParticipationRequest.builder()
                .event(event)
                .requester(user)
                .status((event.getParticipantLimit() == 0 || !event.isRequestModeration())
                        ? RequestStatus.CONFIRMED
                        : RequestStatus.PENDING)
                .created(LocalDateTime.now())
                .build();

        final ParticipationRequest savedRequest = participationRequestRepository.save(newRequest);

        log.info("Запрос создан: id={}, статус={}", savedRequest.getId(), savedRequest.getStatus());
        return ParticipationRequestMapper.toParticipationRequestResponseDto(savedRequest);
    }

    @Override
    public List<ParticipationRequestResponseDto> getUserParticipationRequests(final Long userId) {
        log.info("Получение запросов пользователя id={}", userId);

        entityHelper.getExistingUserByIdOrThrow(userId);

        final List<ParticipationRequestResponseDto> requests = participationRequestRepository.findAllByRequesterId(userId)
                .stream()
                .map(ParticipationRequestMapper::toParticipationRequestResponseDto)
                .toList();

        log.info("Найдено {} запросов у пользователя id={}", requests.size(), userId);
        return requests;
    }

    @Override
    @Transactional
    public ParticipationRequestResponseDto cancelParticipationRequest(final Long userId, final Long requestId) {
        log.info("Отмена запроса id={} пользователем id={}", requestId, userId);

        final ParticipationRequest request = getExistingParticipationRequestByIdOrThrow(requestId);

        if (!request.getRequester().getId().equals(userId)) {
            throw new NotFoundException(String.format(
                    "Запрос id=%d не найден у пользователя id=%d", requestId, userId));
        }

        request.setStatus(RequestStatus.CANCELED);
        final ParticipationRequest canceledRequest = participationRequestRepository.save(request);

        log.info("Запрос id={} отменён", canceledRequest.getId());
        return ParticipationRequestMapper.toParticipationRequestResponseDto(canceledRequest);
    }

    private ParticipationRequest getExistingParticipationRequestByIdOrThrow(final Long requestId) {
        return participationRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Запрос id=%d не найден", requestId)));
    }

    private void validateParticipationRequest(final Event event, final Long userId) {
        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Нельзя создать запрос на своё событие");
        }

        if (!EventState.PUBLISHED.equals(event.getState())) {
            throw new ConflictException("Запросы разрешены только для опубликованных событий");
        }

        if (participationRequestRepository.findByRequesterIdAndEventId(userId, event.getId()) != null) {
            throw new ConflictException("Запрос уже существует");
        }

        if (event.getParticipantLimit() != 0) {
            final int confirmedCount = participationRequestRepository
                    .countByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED);

            if (confirmedCount >= event.getParticipantLimit()) {
                throw new ConflictException("Превышен лимит участников");
            }
        }
    }
}

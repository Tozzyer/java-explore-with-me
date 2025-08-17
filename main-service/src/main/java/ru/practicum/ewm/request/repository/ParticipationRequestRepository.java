package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.enums.RequestStatus;
import ru.practicum.ewm.request.model.ParticipationRequest;

import java.util.List;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findAllByRequesterId(final Long userId);

    List<ParticipationRequest> findAllByEventId(final Long eventId);

    ParticipationRequest findByRequesterIdAndEventId(final Long userId, final Long eventId);

    Integer countByEventIdAndStatus(final Long eventId, final RequestStatus status);
}

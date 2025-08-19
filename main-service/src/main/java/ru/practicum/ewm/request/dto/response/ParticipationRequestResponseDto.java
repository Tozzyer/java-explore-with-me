package ru.practicum.ewm.request.dto.response;

import lombok.Builder;
import lombok.Value;
import ru.practicum.ewm.enums.RequestStatus;

@Value
@Builder(toBuilder = true)
public class ParticipationRequestResponseDto {
    Long id;
    String created;
    Long event;
    Long requester;
    RequestStatus status;
}

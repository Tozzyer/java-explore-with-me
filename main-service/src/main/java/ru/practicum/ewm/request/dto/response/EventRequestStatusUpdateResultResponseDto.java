package ru.practicum.ewm.request.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class EventRequestStatusUpdateResultResponseDto {
    List<ParticipationRequestResponseDto> confirmedRequests;
    List<ParticipationRequestResponseDto> rejectedRequests;
}

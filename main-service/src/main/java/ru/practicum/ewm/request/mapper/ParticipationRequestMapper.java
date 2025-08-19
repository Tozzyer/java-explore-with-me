package ru.practicum.ewm.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.request.dto.response.ParticipationRequestResponseDto;
import ru.practicum.ewm.request.model.ParticipationRequest;

import static ru.practicum.ewm.constant.DateTimeFormatters.FORMATTER;

@UtilityClass
public class ParticipationRequestMapper {

    public static ParticipationRequestResponseDto toParticipationRequestResponseDto(final ParticipationRequest request) {
        if (request == null) {
            return null;
        }
        return ParticipationRequestResponseDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .created(request.getCreated().format(FORMATTER))
                .build();
    }
}

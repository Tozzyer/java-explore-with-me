package ru.practicum.ewm.request.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.enums.RequestStatus;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class EventRequestStatusUpdateRequestDto {

    private final List<Long> requestIds;
    private final RequestStatus status;
}

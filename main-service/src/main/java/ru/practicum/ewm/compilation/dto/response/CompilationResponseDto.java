package ru.practicum.ewm.compilation.dto.response;

import lombok.Builder;
import lombok.Value;
import ru.practicum.ewm.event.dto.response.EventShortResponseDto;

import java.util.List;

@Value
@Builder
public class CompilationResponseDto {
    Long id;
    List<EventShortResponseDto> events;
    Boolean pinned;
    String title;
}

package ru.practicum.ewm.compilation.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.compilation.dto.request.NewCompilationRequestDto;
import ru.practicum.ewm.compilation.dto.response.CompilationResponseDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.mapper.EventMapper;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {

    public static Compilation toNewCompilation(NewCompilationRequestDto newCompilationDto) {
        if (newCompilationDto == null) {
            return null;
        }
        return Compilation.builder()
                .pinned(Boolean.TRUE.equals(newCompilationDto.getPinned()))
                .title(newCompilationDto.getTitle())
                .build();
    }

    public static CompilationResponseDto toCompilationResponseDto(Compilation compilation) {
        if (compilation == null) {
            return null;
        }
        List<?> events = compilation.getEvents() != null
                ? compilation.getEvents().stream().map(EventMapper::toEventShortResponseDto).toList()
                : new ArrayList<>();
        return CompilationResponseDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events((List) events)
                .build();
    }
}

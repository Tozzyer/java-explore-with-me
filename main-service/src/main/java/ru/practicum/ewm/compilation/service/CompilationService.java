package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.request.NewCompilationRequestDto;
import ru.practicum.ewm.compilation.dto.request.UpdateCompilationRequestDto;
import ru.practicum.ewm.compilation.dto.response.CompilationResponseDto;

import java.util.List;

public interface CompilationService {

    CompilationResponseDto createCompilation(NewCompilationRequestDto request);

    List<CompilationResponseDto> getAllCompilations(Boolean pinned, int from, int size);

    CompilationResponseDto getCompilationById(Long compilationId);

    CompilationResponseDto updateCompilation(Long compilationId, UpdateCompilationRequestDto request);

    void deleteCompilationById(Long compilationId);
}

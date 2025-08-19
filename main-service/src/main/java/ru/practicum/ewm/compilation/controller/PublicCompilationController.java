package ru.practicum.ewm.compilation.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.compilation.dto.response.CompilationResponseDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import java.util.List;

import static ru.practicum.ewm.support.ControllerConstants.DEFAULT_TEN;
import static ru.practicum.ewm.support.ControllerConstants.DEFAULT_ZERO;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationResponseDto> getAllCompilations(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = DEFAULT_ZERO) @PositiveOrZero int from,
            @RequestParam(defaultValue = DEFAULT_TEN) @Positive int size) {

        Boolean isPinned = pinned;
        int offset = from;
        int limit = size;
        log.info("GET /compilations — выборка подборок: pinned={}, from={}, size={}", isPinned, offset, limit);
        return compilationService.getAllCompilations(isPinned, offset, limit);
    }

    @GetMapping("/{compId}")
    public CompilationResponseDto getCompilationById(@PathVariable("compId") @Positive Long compId) {
        Long compilationId = compId;
        log.info("GET /compilations/{} — получение подборки", compilationId);
        return compilationService.getCompilationById(compilationId);
    }
}

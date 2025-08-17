package ru.practicum.ewm.compilation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.compilation.dto.request.NewCompilationRequestDto;
import ru.practicum.ewm.compilation.dto.request.UpdateCompilationRequestDto;
import ru.practicum.ewm.compilation.dto.response.CompilationResponseDto;
import ru.practicum.ewm.compilation.service.CompilationService;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationResponseDto createCompilation(@Valid @RequestBody NewCompilationRequestDto request) {
        log.info("POST /admin/compilations — администратор создает подборку: title={}", request.getTitle());
        return compilationService.createCompilation(request);
    }

    @PatchMapping("/{compId}")
    public CompilationResponseDto updateCompilation(@PathVariable("compId") @Positive Long compId,
                                                    @Valid @RequestBody UpdateCompilationRequestDto request) {
        Long compilationId = compId;
        log.info("PATCH /admin/compilations/{} — обновление подборки: title={}, pinned={}, events={}",
                compilationId, request.getTitle(), request.getPinned(), request.getEvents());
        return compilationService.updateCompilation(compilationId, request);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilationById(@PathVariable("compId") @Positive Long compId) {
        Long compilationId = compId;
        log.info("DELETE /admin/compilations/{} — администратор удаляет подборку", compilationId);
        compilationService.deleteCompilationById(compilationId);
    }
}

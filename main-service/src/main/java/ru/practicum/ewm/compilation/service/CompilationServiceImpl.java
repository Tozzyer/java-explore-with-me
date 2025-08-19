package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.dto.request.NewCompilationRequestDto;
import ru.practicum.ewm.compilation.dto.request.UpdateCompilationRequestDto;
import ru.practicum.ewm.compilation.dto.response.CompilationResponseDto;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.support.EntityHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EntityHelper entityHelper;

    @Override
    @Transactional
    public CompilationResponseDto createCompilation(NewCompilationRequestDto newCompilationDto) {
        String title = newCompilationDto.getTitle() != null ? newCompilationDto.getTitle().trim() : null;
        log.info("Создание подборки: title={}", title);

        Compilation compilation = CompilationMapper.toNewCompilation(newCompilationDto);
        compilation.setTitle(title);
        compilation.setEvents(getEventsByIdsOrThrow(newCompilationDto.getEvents()));

        Compilation saved = compilationRepository.save(compilation);
        log.info("Подборка создана: id={}, title={}", saved.getId(), saved.getTitle());
        return CompilationMapper.toCompilationResponseDto(saved);
    }

    @Override
    public List<CompilationResponseDto> getAllCompilations(Boolean pinned, int from, int size) {
        log.info("Запрос списка подборок: pinned={}, from={}, size={}", pinned, from, size);

        Pageable pageable = entityHelper.toPageRequest(from, size);
        List<Compilation> compilations = pinned != null
                ? compilationRepository.findAllByPinned(pinned, pageable)
                : compilationRepository.findAll(pageable).getContent();

        return compilations.stream()
                .map(CompilationMapper::toCompilationResponseDto)
                .toList();
    }

    @Override
    public CompilationResponseDto getCompilationById(Long compId) {
        log.info("Запрос подборки: id={}", compId);

        Compilation compilation = getExistingCompilationByIdOrThrow(compId);
        log.info("Подборка найдена: id={}, title={}", compilation.getId(), compilation.getTitle());
        return CompilationMapper.toCompilationResponseDto(compilation);
    }

    @Override
    @Transactional
    public CompilationResponseDto updateCompilation(Long compId, UpdateCompilationRequestDto updateCompilationDto) {
        log.info("Обновление подборки: id={}, title={}", compId, updateCompilationDto.getTitle());

        Compilation current = getExistingCompilationByIdOrThrow(compId);
        applyUpdates(current, updateCompilationDto);

        Compilation updated = compilationRepository.save(current);
        log.info("Подборка обновлена: id={}, title={}", updated.getId(), updated.getTitle());
        return CompilationMapper.toCompilationResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteCompilationById(Long compId) {
        log.info("Удаление подборки: id={}", compId);

        getExistingCompilationByIdOrThrow(compId);
        compilationRepository.deleteById(compId);

        log.info("Подборка удалена: id={}", compId);
    }

    private Compilation getExistingCompilationByIdOrThrow(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Подборка с id=%d не найдена", compId)));
    }

    private Set<Event> getEventsByIdsOrThrow(List<Long> eventIds) {
        if (eventIds == null || eventIds.isEmpty()) {
            return new HashSet<>();
        }
        return eventIds.stream()
                .map(entityHelper::getExistingEventByIdOrThrow)
                .collect(java.util.stream.Collectors.toSet());
    }

    private void applyUpdates(Compilation compilation, UpdateCompilationRequestDto request) {
        if (request.getPinned() != null) {
            compilation.setPinned(request.getPinned());
        }
        if (request.getTitle() != null) {
            compilation.setTitle(request.getTitle().trim());
        }
        if (request.getEvents() != null) {
            compilation.setEvents(getEventsByIdsOrThrow(request.getEvents()));
        }
    }
}

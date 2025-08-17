package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.request.NewCategoryRequestDto;
import ru.practicum.ewm.category.dto.request.UpdateCategoryRequestDto;
import ru.practicum.ewm.category.dto.response.CategoryResponseDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.support.EntityHelper;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EntityHelper entityHelper;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CategoryResponseDto createCategory(NewCategoryRequestDto newCategoryDto) {
        String name = newCategoryDto.getName() == null ? null : newCategoryDto.getName().trim();
        log.info("Создание категории: name={}", name);

        ensureNameUniqueOrThrow(name, null);

        Category toSave = Category.builder()
                .name(name)
                .build();

        Category saved = categoryRepository.save(toSave);
        log.info("Категория создана: id={}, name='{}'", saved.getId(), saved.getName());
        return CategoryMapper.toCategoryResponseDto(saved);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories(int from, int size) {
        log.info("Запрос списка категорий: from={}, size={}", from, size);

        Pageable pageable = entityHelper.toPageRequest(from, size);

        List<CategoryResponseDto> result = categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryResponseDto)
                .toList();

        log.info("Возвращено категорий: {}", result.size());
        return result;
    }

    @Override
    public CategoryResponseDto getCategoryById(Long catId) {
        log.info("Получение категории по id={}", catId);

        Category category = entityHelper.getExistingCategoryByIdOrThrow(catId);

        log.info("Категория найдена: id={}, name='{}'", category.getId(), category.getName());
        return CategoryMapper.toCategoryResponseDto(category);
    }

    @Override
    @Transactional
    public CategoryResponseDto updateCategory(UpdateCategoryRequestDto updateCategoryDto, Long catId) {
        String newName = updateCategoryDto.getName() == null ? null : updateCategoryDto.getName().trim();
        log.info("Обновление категории: id={}, новое имя={}", catId, newName);

        Category current = entityHelper.getExistingCategoryByIdOrThrow(catId);

        if (Objects.equals(current.getName(), newName)) {
            log.info("Имя категории не изменилось: id={}, name='{}'", catId, newName);
            return CategoryMapper.toCategoryResponseDto(current);
        }

        ensureNameUniqueOrThrow(newName, catId);

        current.setName(newName);
        Category updated = categoryRepository.save(current);

        log.info("Категория обновлена: id={}, name='{}'", updated.getId(), updated.getName());
        return CategoryMapper.toCategoryResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long catId) {
        log.info("Удаление категории: id={}", catId);

        entityHelper.getExistingCategoryByIdOrThrow(catId);

        List<Event> linkedEvents = eventRepository.findByCategoryId(catId);
        if (!linkedEvents.isEmpty()) {
            throw new ConflictException("Категория содержит связанные события");
        }

        categoryRepository.deleteById(catId);
        log.info("Категория удалена: id={}", catId);
    }

    private void ensureNameUniqueOrThrow(String name, Long excludeId) {
        Category sameName = categoryRepository.findByName(name);
        if (sameName != null && (excludeId == null || !Objects.equals(sameName.getId(), excludeId))) {
            throw new ConflictException(String.format("Название категории '%s' уже занято", name));
        }
    }
}

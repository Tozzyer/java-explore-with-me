package ru.practicum.ewm.category.controller;

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
import ru.practicum.ewm.category.dto.response.CategoryResponseDto;
import ru.practicum.ewm.category.service.CategoryService;

import java.util.List;

import static ru.practicum.ewm.support.ControllerConstants.DEFAULT_TEN;
import static ru.practicum.ewm.support.ControllerConstants.DEFAULT_ZERO;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDto> getAllCategories(
            @RequestParam(defaultValue = DEFAULT_ZERO) @PositiveOrZero int from,
            @RequestParam(defaultValue = DEFAULT_TEN) @Positive int size) {

        int offset = from;
        int limit = size;
        log.info("GET /categories — выборка категорий: from={}, size={}", offset, limit);
        return categoryService.getAllCategories(offset, limit);
    }

    @GetMapping("/{catId}")
    public CategoryResponseDto getCategoryById(@PathVariable("catId") @Positive Long catId) {
        Long categoryId = catId;
        log.info("GET /categories/{} — получение сведений о категории", categoryId);
        return categoryService.getCategoryById(categoryId);
    }
}

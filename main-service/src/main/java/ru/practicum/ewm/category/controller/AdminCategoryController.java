package ru.practicum.ewm.category.controller;

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
import ru.practicum.ewm.category.dto.request.NewCategoryRequestDto;
import ru.practicum.ewm.category.dto.request.UpdateCategoryRequestDto;
import ru.practicum.ewm.category.dto.response.CategoryResponseDto;
import ru.practicum.ewm.category.service.CategoryService;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@Valid @RequestBody NewCategoryRequestDto request) {
        log.info("POST /admin/categories — администратор добавляет категорию: name={}", request.getName());
        return categoryService.createCategory(request);
    }

    @PatchMapping("/{catId}")
    public CategoryResponseDto updateCategory(@Valid @RequestBody UpdateCategoryRequestDto request,
                                              @PathVariable("catId") @Positive Long categoryId) {
        log.info("PATCH /admin/categories/{} — правка категории: name={}", categoryId, request.getName());
        return categoryService.updateCategory(request, categoryId);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable("catId") @Positive Long categoryId) {
        log.info("DELETE /admin/categories/{} — администратор удаляет категорию", categoryId);
        categoryService.deleteCategoryById(categoryId);
    }
}

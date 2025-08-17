package ru.practicum.ewm.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleCustomNotFoundException(final NotFoundException exception) {
        return new ApiError(
                exception.getMessage(),
                "Сущность не найдена",
                HttpStatus.NOT_FOUND.name()
        );
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleCustomValidationException(final ValidationException exception) {
        log.warn("Ошибка бизнес-логики {}", exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                "Некорректные данные",
                HttpStatus.BAD_REQUEST.name()
        );
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleCustomConflictException(final ConflictException exception) {
        return new ApiError(
                exception.getMessage(),
                "Конфликт с текущим состоянием",
                HttpStatus.CONFLICT.name()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("Поле: %s. Ошибка: %s", error.getField(), error.getDefaultMessage()))
                .toList();

        return new ApiError(
                errors,
                "Неверные данные запроса",
                "Ошибка валидации",
                HttpStatus.BAD_REQUEST.name()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(final ConstraintViolationException exception) {
        final List<String> errors = exception.getConstraintViolations().stream()
                .map(v -> String.format("Параметр: %s. Ошибка: %s", v.getPropertyPath(), v.getMessage()))
                .toList();

        return new ApiError(
                errors,
                "Некорректные параметры",
                "Ошибка валидации",
                HttpStatus.BAD_REQUEST.name()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleJsonParseError(final HttpMessageNotReadableException exception) {
        return new ApiError(
                exception.getMessage(),
                "Ошибочный формат запроса",
                HttpStatus.BAD_REQUEST.name()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolation(final DataIntegrityViolationException exception) {
        return new ApiError(
                exception.getMessage(),
                "Нарушение уникальности или связности данных",
                HttpStatus.CONFLICT.name()
        );
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleHandlerMethodValidationException(final HandlerMethodValidationException exception) {
        final List<String> errors = exception.getAllErrors().stream()
                .map(err -> String.format("Ошибка: %s", err.getDefaultMessage()))
                .toList();

        return new ApiError(
                errors,
                "Аргументы метода не прошли проверку",
                "Ошибка валидации",
                HttpStatus.BAD_REQUEST.name()
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNoResourceFound(final NoResourceFoundException exception) {
        return new ApiError(
                exception.getMessage(),
                "Ресурс не существует или путь некорректен",
                HttpStatus.NOT_FOUND.name()
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleServletRequestParameterException(final MissingServletRequestParameterException exception) {
        return new ApiError(
                exception.getMessage(),
                "Отсутствует обязательный параметр",
                HttpStatus.BAD_REQUEST.name()
        );
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleUnexpectedError(final Throwable exception) {
        log.error("Непредвиденное исключение", exception);
        return new ApiError(
                "Внутренняя ошибка сервера",
                "Необработанное исключение",
                HttpStatus.INTERNAL_SERVER_ERROR.name()
        );
    }
}

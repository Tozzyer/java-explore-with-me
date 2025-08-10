package ru.practicum.ewm.server.exceptionshandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCustomValidationException(
            final ValidationException ex,
            final HttpServletRequest request
    ) {
        log.warn("Ошибка валидации: {}", ex.getMessage());
        return new ErrorResponse(
                "Данные некорректны",
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException ex,
            final HttpServletRequest request
    ) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        log.warn("JSON ошибка: {}", errorMessage);

        return new ErrorResponse(
                "JSON ошибка",
                errorMessage,
                request.getRequestURI(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            final ConstraintViolationException ex,
            final HttpServletRequest request
    ) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));

        log.warn("Неверные параметры: {}", errorMessage);

        return new ErrorResponse(
                "Некорректный запрос",
                errorMessage,
                request.getRequestURI(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDeserializationException(
            final HttpMessageNotReadableException ex,
            final HttpServletRequest request
    ) {
        log.warn("Неверные данные: {}", ex.getMessage());

        return new ErrorResponse(
                "Неверные данные",
                "Неверный формат JSON",
                request.getRequestURI(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedException(
            final Exception ex,
            final HttpServletRequest request
    ) {
        log.error("Ошибка сервера: {}", ex.getMessage(), ex);

        return new ErrorResponse(
                "Ошибка сервера",
                "Ошибка сервера",
                request.getRequestURI(),
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }
}

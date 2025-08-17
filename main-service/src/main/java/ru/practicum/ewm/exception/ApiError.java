package ru.practicum.ewm.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static ru.practicum.ewm.constant.DateTimeFormatters.DATE_TIME_FORMAT;

@Getter
public class ApiError {

    private final String message;
    private final String reason;
    private final String status;
    private final List<String> errors;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private final LocalDateTime timestamp;

    public ApiError(String message, String reason, String status) {
        this(Collections.emptyList(), message, reason, status);
    }

    public ApiError(List<String> errors, String message, String reason, String status) {
        this.errors = Objects.requireNonNullElse(errors, Collections.emptyList());
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}

package ru.practicum.ewm.server.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class ErrorResponse {

    private final String status;
    private final String reason;
    private final String message;
    private final String timestamp;
}

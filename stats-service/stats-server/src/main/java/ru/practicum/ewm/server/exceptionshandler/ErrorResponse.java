package ru.practicum.ewm.server.exceptionshandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String error;
    private final String description;
    private final String path;
    private final LocalDateTime timestamp;
    private final int status;
}

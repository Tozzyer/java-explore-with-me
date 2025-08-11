package ru.practicum.ewm.support;

import java.time.format.DateTimeFormatter;

public final class DateTimeFormatters {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    private DateTimeFormatters() {
        // Utility
    }
}

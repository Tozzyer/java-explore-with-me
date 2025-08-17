package ru.practicum.ewm.enums;

import lombok.Getter;

@Getter
public enum RequestStatus {
    PENDING("В ожидании"),
    CONFIRMED("Подтверждено"),
    REJECTED("Отклонено"),
    CANCELED("Отменено");

    private final String description;

    RequestStatus(String description) {
        this.description = description;
    }
}

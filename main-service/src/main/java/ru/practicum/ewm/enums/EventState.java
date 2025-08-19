package ru.practicum.ewm.enums;

import lombok.Getter;

@Getter
public enum EventState {
    PENDING("Ожидает подтверждения"),
    PUBLISHED("Опубликовано"),
    CANCELED("Отменено");

    private final String description;

    EventState(String description) {
        this.description = description;
    }
}

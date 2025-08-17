package ru.practicum.ewm.enums;

import lombok.Getter;

@Getter
public enum AdminStateAction {
    PUBLISH_EVENT("Публикация события"),
    REJECT_EVENT("Отклонение события");

    private final String description;

    AdminStateAction(String description) {
        this.description = description;
    }
}

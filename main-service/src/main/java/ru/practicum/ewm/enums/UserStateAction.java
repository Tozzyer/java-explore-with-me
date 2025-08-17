package ru.practicum.ewm.enums;

import lombok.Getter;

@Getter
public enum UserStateAction {
    SEND_TO_REVIEW("Отправить на модерацию"),
    CANCEL_REVIEW("Отозвать с модерации");

    private final String description;

    UserStateAction(String description) {
        this.description = description;
    }
}

package ru.practicum.ewm.event.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.location.dto.LocationDto;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NewEventRequestDto {

    @NotBlank(message = "Аннотация события не должна быть пустой")
    @Size(min = 20, max = 2000, message = "Аннотация должна содержать от 20 до 2000 символов")
    private String annotation;

    @NotNull(message = "Категория события должна быть указана")
    private Long category;

    @NotBlank(message = "Описание события не должно быть пустым")
    @Size(min = 20, max = 7000, message = "Описание должно содержать от 20 до 7000 символов")
    private String description;

    @NotBlank(message = "Дата и время проведения события должны быть указаны")
    private String eventDate;

    @NotNull(message = "Локация события должна быть указана")
    private LocationDto location;

    @Builder.Default
    private boolean paid = false;

    @PositiveOrZero(message = "Лимит участников должен быть не меньше 0")
    @Builder.Default
    private int participantLimit = 0;

    @Builder.Default
    private boolean requestModeration = true;

    @NotBlank(message = "Заголовок события не должен быть пустым")
    @Size(min = 3, max = 120, message = "Заголовок должен содержать от 3 до 120 символов")
    private String title;
}

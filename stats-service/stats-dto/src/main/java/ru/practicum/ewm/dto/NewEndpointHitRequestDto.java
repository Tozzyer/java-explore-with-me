package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static ru.practicum.ewm.support.DateTimeFormatters.DATE_TIME_FORMAT;

/**
 * DTO для регистрации хита статистики.
 * Поведение сохранено без изменений.
 */
@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NewEndpointHitRequestDto {

    @NotBlank
    @Size(max = 255)
    private String app;

    @NotBlank
    @Size(max = 255)
    @Pattern(regexp = "^/.*")
    private String uri;

    @NotBlank
    @Size(max = 255)
    private String ip;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    @NotNull
    private String timestamp;
}

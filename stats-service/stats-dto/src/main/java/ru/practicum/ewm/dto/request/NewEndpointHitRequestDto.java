package ru.practicum.ewm.dto.request;

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

import static ru.practicum.ewm.constant.DateTimeFormatters.DATE_TIME_FORMAT;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NewEndpointHitRequestDto {

    @NotBlank(message = "Название сервиса не может быть пустым")
    @Size(max = 255, message = "Название сервиса допускается длиной не более 255 символов")
    private String app;

    @NotBlank(message = "URI обязателен для заполнения")
    @Size(max = 255, message = "URI может содержать максимум 255 символов")
    @Pattern(regexp = "^/.*", message = "URI должен начинаться со знака '/'")
    private String uri;

    @NotBlank(message = "IP-адрес обязателен для указания")
    @Size(max = 45, message = "IP-адрес не может быть длиннее 45 символов")
    private String ip;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    @NotNull(message = "Дата и время запроса должны быть заданы")
    private String timestamp;
}

package ru.practicum.ewm.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentRequestDto {

    @NotBlank(message = "Текст комментария не может быть пустым")
    @Size(min = 30, max = 2000, message = "Длина комментария должна быть от 30 до 2000 символов")
    private String text;
}

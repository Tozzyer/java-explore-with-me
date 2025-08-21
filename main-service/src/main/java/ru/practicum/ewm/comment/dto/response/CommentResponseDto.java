package ru.practicum.ewm.comment.dto.response;

import lombok.Builder;
import lombok.Value;
import ru.practicum.ewm.user.dto.response.UserShortResponseDto;

@Value
@Builder
public class CommentResponseDto {
    Long id;
    String text;
    UserShortResponseDto author;
    String createdOn;
    String updatedOn;
}


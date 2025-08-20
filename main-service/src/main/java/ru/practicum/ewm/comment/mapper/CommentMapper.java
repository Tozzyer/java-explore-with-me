package ru.practicum.ewm.comment.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.comment.dto.request.NewCommentRequestDto;
import ru.practicum.ewm.comment.dto.response.CommentResponseDto;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.user.mapper.UserMapper;

import static ru.practicum.ewm.constant.DateTimeFormatters.FORMATTER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static Comment toNewComment(NewCommentRequestDto newCommentDto) {
        if (newCommentDto == null) {
            return null;
        }
        return Comment.builder()
                .text(newCommentDto.getText())
                .build();
    }

    public static CommentResponseDto toCommentResponseDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        return CommentResponseDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .author(UserMapper.toUserShortResponseDto(comment.getAuthor()))
                .createdOn(comment.getCreatedOn() != null ? comment.getCreatedOn().format(FORMATTER) : null)
                .updatedOn(comment.getUpdatedOn() != null ? comment.getUpdatedOn().format(FORMATTER) : null)
                .build();
    }
}

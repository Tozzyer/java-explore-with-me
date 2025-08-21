package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.request.UpdateCommentRequestDto;
import ru.practicum.ewm.comment.dto.response.CommentResponseDto;

public interface AdminCommentService {

    CommentResponseDto updateCommentByAdmin(UpdateCommentRequestDto request, Long evtId, Long cmtId);

    void deleteCommentByAdmin(Long evtId, Long cmtId);
}

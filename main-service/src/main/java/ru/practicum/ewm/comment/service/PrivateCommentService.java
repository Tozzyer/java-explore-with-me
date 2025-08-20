package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.request.NewCommentRequestDto;
import ru.practicum.ewm.comment.dto.request.UpdateCommentRequestDto;
import ru.practicum.ewm.comment.dto.response.CommentResponseDto;

public interface PrivateCommentService {

    CommentResponseDto createComment(NewCommentRequestDto request, Long uId, Long evtId);

    CommentResponseDto updateComment(UpdateCommentRequestDto request,
                                     Long uId,
                                     Long evtId,
                                     Long cmtId);

    void deleteComment(Long uId, Long evtId, Long cmtId);
}

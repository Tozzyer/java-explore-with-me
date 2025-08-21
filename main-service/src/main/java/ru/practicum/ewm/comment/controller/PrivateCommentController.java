package ru.practicum.ewm.comment.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.comment.dto.request.NewCommentRequestDto;
import ru.practicum.ewm.comment.dto.request.UpdateCommentRequestDto;
import ru.practicum.ewm.comment.dto.response.CommentResponseDto;
import ru.practicum.ewm.comment.service.PrivateCommentService;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users/{userId}/events/{eventId}/comments")
public class PrivateCommentController {

    private final PrivateCommentService privateCommentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@Valid @RequestBody NewCommentRequestDto newCommentDto,
                                            @PathVariable("userId") @Positive Long userId,
                                            @PathVariable("eventId") @Positive Long eventId) {

        log.info("POST /users/{}/events/{}/comments — пользователь {} оставил комментарий к событию {}",
                userId, eventId, userId, eventId);
        return privateCommentService.createComment(newCommentDto, userId, eventId);
    }

    @PatchMapping("/{commentId}")
    public CommentResponseDto updateComment(@Valid @RequestBody UpdateCommentRequestDto updateCommentDto,
                                            @PathVariable("userId") @Positive Long userId,
                                            @PathVariable("eventId") @Positive Long eventId,
                                            @PathVariable("commentId") @Positive Long commentId) {

        log.info("PATCH /users/{}/events/{}/comments/{} — пользователь {} изменил комментарий к событию {}",
                userId, eventId, commentId, userId, eventId);
        return privateCommentService.updateComment(updateCommentDto, userId, eventId, commentId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("userId") @Positive Long userId,
                              @PathVariable("eventId") @Positive Long eventId,
                              @PathVariable("commentId") @Positive Long commentId) {

        log.info("DELETE /users/{}/events/{}/comments/{} — пользователь {} удалил комментарий к событию {}",
                userId, eventId, commentId, userId, eventId);
        privateCommentService.deleteComment(userId, eventId, commentId);
    }
}

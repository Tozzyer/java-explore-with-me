package ru.practicum.ewm.comment.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.comment.dto.response.CommentResponseDto;
import ru.practicum.ewm.comment.service.PublicCommentService;

import java.util.List;

import static ru.practicum.ewm.support.ControllerConstants.DEFAULT_TEN;
import static ru.practicum.ewm.support.ControllerConstants.DEFAULT_ZERO;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/events/{eventId}/comments")
public class PublicCommentController {

    private final PublicCommentService publicCommentService;

    @GetMapping
    public List<CommentResponseDto> getAllCommentsByEvent(
            @PathVariable("eventId") @Positive Long eventId,
            @RequestParam(defaultValue = DEFAULT_ZERO) @PositiveOrZero int from,
            @RequestParam(defaultValue = DEFAULT_TEN) @Positive int size) {

        log.info("GET /events/{}/comments — список комментариев к событию {}", eventId, eventId);
        return publicCommentService.getAllCommentsByEvent(eventId, from, size);
    }

    @GetMapping("/{commentId}")
    public CommentResponseDto getCommentByEventAndCommentId(@PathVariable("eventId") @Positive Long eventId,
                                                            @PathVariable("commentId") @Positive Long commentId) {

        log.info("GET /events/{}/comments/{} — комментарий к событию {}", eventId, commentId, eventId);
        return publicCommentService.getCommentByEventAndCommentId(eventId, commentId);
    }
}

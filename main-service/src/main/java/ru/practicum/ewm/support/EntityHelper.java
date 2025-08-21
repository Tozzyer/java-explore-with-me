package ru.practicum.ewm.support;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class EntityHelper {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public Category getExistingCategoryByIdOrThrow(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Категория id=%d не найдена", categoryId))
                );
    }


    public Event getExistingEventByIdOrThrow(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие id=%d не найдено", eventId))
                );
    }

    public User getExistingUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь id=%d не найден", userId))
                );
    }


    public Pageable toPageRequest(int from, int size) {
        int page = from / size;
        return PageRequest.of(page, size);
    }

    public Comment getExistingCommentByIdOrThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Отсутствует комментарий id=%d", commentId)));
    }

    public void validateCommentBelongsToEvent(Long eventId, Comment comment) {
        if (!Objects.equals(eventId, comment.getEvent().getId())) {
            throw new ConflictException(
                    String.format("Событие id=%d не матчится с комментарием id=%d", eventId, comment.getId()));
        }
    }

    public void validateCommentOwnership(Long userId, Long eventId, Comment comment) {
        validateCommentBelongsToEvent(eventId, comment);
        if (!Objects.equals(comment.getAuthor().getId(), userId)) {
            throw new ConflictException(
                    String.format("Пользователь id=%d не матчится с комментарием id=%d", userId, comment.getId())
            );
        }
    }
}

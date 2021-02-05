package io.spring.service;

import io.spring.entity.Comment;

import java.util.List;

/**
 * @author Elghareb
 */
public interface CommentService {

    Comment addComment(Long articleId, Comment comment);

    List<Comment> getCommentsByArticleId(Long articleId);

    void deleteComment(Long articleId, Long userId, Long commentId);

}

package io.spring.dao;

import io.spring.entity.Comment;

import java.util.Optional;

public interface CommentDao {
    void save(Comment comment);

    void delete(Comment comment);

    Optional<Comment> findById(Long id);
}

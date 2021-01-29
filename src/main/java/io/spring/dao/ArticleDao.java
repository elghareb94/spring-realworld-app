package io.spring.dao;

import io.spring.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleDao {
    void save(Article article);

    void update(Article article);

    void delete(Article article);

    Optional<Article> findById(Long id);

    Optional<Article> findBySlug(String slug);

    List<Article> findAll();

    List<Article> findByAuthors(List<Long> ids);
}

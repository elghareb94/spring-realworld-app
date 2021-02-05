package io.spring.service;

import io.spring.entity.Article;
import io.spring.entity.User;

import java.util.List;

public interface ArticleService {

    Article create(Article article);

    List<Article> getFeed(Long userId);

    List<Article> getAll();

    Article findById(Long articleId);

    Article update(Article article, Long userId);

    void delete(Long articleId, Long userId);

    Article addToFavorite(Long articleId, User user);

    Article removeFavorite(Long articleId, User user);
}

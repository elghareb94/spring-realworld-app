package io.spring.service.impl;

import io.spring.dao.ArticleDao;
import io.spring.dao.UserDao;
import io.spring.entity.Article;
import io.spring.entity.User;
import io.spring.exception.exceptions.ResourceNotFoundException;
import io.spring.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Elghareb
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Article create(Article article) {
        articleDao.save(article);
        return article;
    }

    @Override
    public Article update(Article article, Long userId) {
        if (article.getAuthor().getId() != userId) {
            //not allowed
        }
        articleDao.update(article);
        return article;
    }

    @Override
    public void delete(Long articleId, Long userId) {

        Optional<Article> article = articleDao.findById(articleId);

        if (article.isEmpty()) {
            throw new ResourceNotFoundException(Article.class, "id", String.valueOf(articleId));
        }
//        if(article.get().getAuthor().getId() != userId){
//            //not allowed
//        }
        articleDao.delete(article.get());
    }

    @Override
    public Article findById(Long articleId) {
        return articleDao.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(Article.class, "id", String.valueOf(articleId)));
    }

//    @Override
//    public Article findBySlug(String slug, Long userId) {
//        return articleDao.findBySlug(slug)
//                .orElseThrow(() -> new ResourceNotFoundException(Article.class,"Slug", slug));
//    }

    @Override
    public List<Article> getFeed(Long userId) {

        Optional<User> currentUser = userDao.findById(userId);
        if (currentUser.isEmpty()) {
            throw new ResourceNotFoundException(User.class, "Id", String.valueOf(userId));
        }

        List<Long> authorIds = currentUser.get().getFollowings().stream().map(u -> u.getId()).collect(Collectors.toList());
        List<Article> articles = articleDao.findByAuthors(authorIds);
        return articles;
    }

    @Override
    public List<Article> getAll() {
        return articleDao.findAll();
    }

    public Article addToFavorite(Long articleId, User user) {
        Optional<Article> article = articleDao.findById(articleId);
        if (article.isEmpty()) {
            throw new ResourceNotFoundException(Article.class, "Id", String.valueOf(articleId));
        }


        article.get().getFavoriteUsers().add(user);
        articleDao.update(article.get());
        return article.get();
    }

    @Override
    public Article removeFavorite(Long articleId, User user) {

        Optional<Article> article = articleDao.findById(articleId);
        if (article.isEmpty()) {
            throw new ResourceNotFoundException(Article.class, "Id", String.valueOf(articleId));
        }


        article.get().getFavoriteUsers().removeIf((u) -> {
            return u.getId().equals(user.getId());
        });
        articleDao.update(article.get());
        return article.get();
    }


}

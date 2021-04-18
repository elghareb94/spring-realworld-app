package io.spring.service;

import io.spring.dao.ArticleDao;
import io.spring.dao.UserDao;
import io.spring.entity.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleDao articleDao;
    @Mock
    private UserDao userDao;

    @Autowired
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
//        articleService = new ArticleServiceImpl(articleDao, userDao);
    }

    @Test
    void create() {
        Article article1 = Article.builder()
                .description("desc")
                .title("title")
                .body("body")
                .build();

        Article article2 = Article.builder()
                .description("desc")
                .title("title")
                .body("body")
                .build();

        articleService.create(article1);
        verify(articleDao).save(article2);
    }
}
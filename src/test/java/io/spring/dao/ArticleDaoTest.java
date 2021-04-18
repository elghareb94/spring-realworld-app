package io.spring.dao;

import io.spring.config.TestAppConfig;
import io.spring.entity.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//assertj

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class})
@ActiveProfiles("test")
@Transactional
class ArticleDaoTest {

    @Autowired
    private ArticleDao articleDao;

    @BeforeEach
    public void setUp() {

    }

    @Test
    void testFindById() {
        Article article = Article.builder().description("des").title("title").body("body").build();
        articleDao.save(article);

        Optional<Article> optionalArticle = articleDao.findById(article.getId());
        Assertions.assertThat(article).isEqualTo(optionalArticle.get());


    }

}

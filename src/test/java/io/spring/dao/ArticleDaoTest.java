package io.spring.dao;

import io.spring.config.TestAppConfig;
import io.spring.entity.Article;
import io.spring.entity.Tag;
import io.spring.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class})
@ActiveProfiles("test")
@Transactional
class ArticleDaoTest {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private UserDao userDao;

    private User user;

    private Article article;

    @BeforeEach
    public void setUp() {

        user = User.builder()
                .email("elghareb94@gmail.com")
                .bio("bio bio")
                .username("ahmed")
                .password("pass")
                .build();

        userDao.save(user);

        article = Article.builder()
                .body("body body")
                .description("desc desc")
                .title("title title")
                .tags(Set.of(new Tag("Java"), new Tag("C++"), new Tag("Python")))
                .author(user)
                .build();

        articleDao.save(article);
    }

    @Test
    void should_fetch_article_success() {

        Optional<Article> optional = articleDao.findById(article.getId());
        assertThat(optional.isPresent()).isEqualTo(true);

        Article fetchedArticle = optional.get();
        assertThat(fetchedArticle.getFavoriteUsers().size()).isEqualTo(0);
        assertThat(fetchedArticle.getCreatedAt()).isNotNull();
        assertThat(fetchedArticle.getUpdatedAt()).isNotNull();
        assertThat(fetchedArticle.getTags().size()).isEqualTo(3);
    }

    @Test
    public void should_get_article_with_right_favorite_and_favorite_count() {

        User anotherUser = User.builder()
                .email("other@test.com")
                .username("other")
                .password("123")
                .bio("bio bio")
                .imageUrl("img")
                .build();

        article.addFavoriteUser(user);

        userDao.save(anotherUser);

        Optional<Article> optional = articleDao.findById(article.getId());
        assertThat(optional.isPresent()).isTrue();

        Article fetchedArticle = optional.get();
        anotherUser = userDao.findById(user.getId()).get();

        assertThat(anotherUser.getFavoriteArticles().size()).isEqualTo(1);
        assertThat(fetchedArticle.getFavoriteUsers().size()).isEqualTo(1);

    }


    @Test
    public void should_query_article_by_author() {

        User anotherUser = User.builder()
                .email("other@test.com")
                .username("other")
                .password("123")
                .bio("bio bio")
                .imageUrl("img")
                .build();

        userDao.save(anotherUser);


        Article anotherArticle = Article.builder()
                .title("new article")
                .description("desc")
                .body("body")
                .tags(Set.of(new Tag("Java"), new Tag("C++"), new Tag("Python")))
                .author(anotherUser)
                .build();


        articleDao.save(anotherArticle);

        List<Article> recentArticles = articleDao.findByAuthors(Arrays.asList(anotherUser.getId()));

        assertThat(recentArticles.size()).isEqualTo(1);
    }


}

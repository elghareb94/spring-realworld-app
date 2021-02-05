package io.spring.dao;

import io.spring.config.AppConfig;
import io.spring.config.DataSourceConfig;
import io.spring.entity.Article;
import io.spring.entity.Comment;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, AppConfig.class})
@ActiveProfiles("test")
@Transactional
public class ArticleDaoTest {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private SessionFactory sessionFactory;

    private Article article;

    @Before
    public void setUp() {
        Set<Comment> comments = new HashSet<>();
        comments.add(Comment.builder().body("aaaaaa").build());

        article = Article.builder().slug("slug").description("Desc").comments(comments).body("body").build();
        articleDao.save(article);
        sessionFactory.getCurrentSession().detach(article);
    }

    @Test
    public void testFindById() {
        Optional<Article> optionalArticle = articleDao.findById(article.getId());
        Assert.assertEquals(article, optionalArticle.get());
        Assert.assertFalse(article == optionalArticle.get());

    }

    @Test
    public void testFindBySlug() {
        Optional<Article> optionalArticle = articleDao.findBySlug(article.getSlug());
        Assert.assertEquals(article, optionalArticle.get());
        Assert.assertFalse(article == optionalArticle.get());
    }

    @Test
    public void testFindAll() {
        List<Article> articles = articleDao.findAll();
        Assert.assertEquals(article, articles.get(0));
        Assert.assertEquals(articles.size(), 1);
        Assert.assertFalse(article == articles.get(0));
    }

    @Test
    public void testUpdate() {
        article.setDescription("newDesc");
        article.setBody("new Body");
        articleDao.update(article);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().detach(article);

        Optional<Article> optionalArticle = articleDao.findById(article.getId());
        Assert.assertEquals(article, optionalArticle.get());
        Assert.assertFalse(article == optionalArticle.get());
    }

    @Test
    public void testDelete() {
        articleDao.delete(article);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().detach(article);

        Optional<Article> optionalArticle = articleDao.findById(article.getId());
        Assert.assertFalse(optionalArticle.isPresent());
    }
}

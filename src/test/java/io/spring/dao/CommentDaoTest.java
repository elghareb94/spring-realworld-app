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
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, AppConfig.class})
@ActiveProfiles("test")
@Transactional
public class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private SessionFactory sessionFactory;

    private Comment comment;

    private Article article;

    @Before
    public void setUp() {
        comment = Comment.builder()
                .body("body of comment")
                .build();

        HashSet<Comment> comments = new HashSet<>();

        comments.add(comment);
        article = Article.builder()
                .body("body of comment")
                .description("descr of article")
                .slug("slug")
                .comments(comments)
                .build();

        commentDao.save(comment);
        sessionFactory.getCurrentSession().detach(comment);
    }

    @Test
    public void testFindById() {
        Optional<Comment> optionalComment = commentDao.findById(comment.getId());
        Assert.assertEquals(comment, optionalComment.get());
        Assert.assertTrue(comment != optionalComment.get());
    }

    @Test
    public void testDelete() {
        commentDao.delete(comment);
        Optional<Comment> optionalComment = commentDao.findById(comment.getId());
        Assert.assertFalse(optionalComment.isPresent());
    }

}

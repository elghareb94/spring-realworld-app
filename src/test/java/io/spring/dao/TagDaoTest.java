package io.spring.dao;


import io.spring.config.AppConfig;
import io.spring.config.DataSourceConfig;
import io.spring.entity.Article;
import io.spring.entity.Tag;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, AppConfig.class})
@ActiveProfiles("test")
@Transactional
public class TagDaoTest {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private SessionFactory sessionFactory;

    private List<Tag> tags;

    @Before
    public void setUp() {
        tags = new ArrayList<>();
        tags.add(Tag.builder().name("tag1").build());
        tags.add(Tag.builder().name("tag2").build());
        tags.add(Tag.builder().name("tag3").build());
        tags.add(Tag.builder().name("tag4").build());
        tags.add(Tag.builder().name("tag5").build());
        Article article = Article.builder().tags(new HashSet<>(tags)).build();
        articleDao.save(article);
        sessionFactory.getCurrentSession().detach(article);
    }

    @Test
    public void testFindAll() {
        List<Tag> t = tagDao.findAll();
        Assert.assertEquals(t.size(), tags.size());
        Assert.assertTrue(t.containsAll(tags));
        Assert.assertTrue(tags.containsAll(t));
        Assert.assertTrue(t != tags);
    }

}

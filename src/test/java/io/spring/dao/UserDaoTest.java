package io.spring.dao;

import io.spring.config.AppConfig;
import io.spring.config.DataSourceConfig;
import io.spring.entity.User;
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

import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, AppConfig.class})
@ActiveProfiles("test")
@Transactional
public class UserDaoTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private SessionFactory sessionFactory;

    private User user;

    @Before
    public void setUp() {
        user = User.builder()
                .username("username")
                .email("email@mail.com")
                .password("Test1234")
                .build();
        userDao.save(user);
        sessionFactory.getCurrentSession().detach(user);
    }

    @Test
    public void testFindById() {
        Optional<User> optionalUser = userDao.findById(user.getId());
        Assert.assertEquals(user, optionalUser.get());
        Assert.assertTrue(user != optionalUser.get());
    }

    @Test
    public void testFindByEmail() {
        Optional<User> optionalUser = userDao.findByEmail("email@mail.com");
        Assert.assertEquals(user, optionalUser.get());
        Assert.assertTrue(user != optionalUser.get());
    }

    @Test
    public void testUpdate_UpdateUserName() {
        user.setUsername("newUserName");
        userDao.update(user);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().detach(user);

        Optional<User> optionalUser = userDao.findByUsername("newUserName");

        Assert.assertEquals(user, optionalUser.get());
        Assert.assertTrue(user != optionalUser.get());

    }

    @Test
    public void testUpdate_UpdateEMail() {
        user.setEmail("tempEmail");
        userDao.update(user);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().detach(user);

        Optional<User> optionalUser = userDao.findByEmail(user.getEmail());

        Assert.assertEquals(user, optionalUser.get());
        Assert.assertTrue(user != optionalUser.get());
    }

    @Test
    public void testUpdate_UpdatePassword() {
        user.setPassword("newPassword");
        userDao.update(user);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().detach(user);

        Optional<User> optionalUser = userDao.findByEmail(user.getEmail());

        Assert.assertEquals(user, optionalUser.get());
        Assert.assertTrue(user != optionalUser.get());

    }

    @Test
    public void testFindByUsername() {
        Optional<User> optionalUser = userDao.findByUsername(user.getUsername());
        Assert.assertEquals(user, optionalUser.get());
        Assert.assertTrue(user != optionalUser.get());
    }


    @Test
    public void testDelete() {
        userDao.delete(user);
        Optional<User> optionalUser = userDao.findById(1L);
        Assert.assertFalse(optionalUser.isPresent());
    }


}

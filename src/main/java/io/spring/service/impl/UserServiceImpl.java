package io.spring.service.impl;

import io.spring.dao.ArticleDao;
import io.spring.dao.UserDao;
import io.spring.entity.User;
import io.spring.exception.exceptions.ResourceNotFoundException;
import io.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Elghareb
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(this.findByUserName(s));
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", s));
        }
        return org.springframework.security.core.userdetails
                .User.builder()
                .username(user.get().getUsername())
                .password(user.get().getPassword())
                .authorities("ROLE").build();

//        return new JwtUser(user.get().getId(), user.get().getUsername(), user.get().getPassword(), "ROLE");
    }

    @Override
    public User findById(Long userId) {
        return userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "Id", String.valueOf(userId)));
    }

    @Override
    public User findByUserName(String userName) {
        return userDao.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "username", userName));
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "email", email));
    }

    @Override
    public User update(User user) {
        userDao.update(user);
        return user;
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return user;
    }

    public void delete(Long userId) {
        Optional<User> user = userDao.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException(User.class, "id", String.valueOf(userId));
        }
        user.get().getFollowers().forEach(c -> {
            c.getFollowings().removeIf(user1 -> user1.getId() == userId);
            userDao.save(c);
        });

        user.get().getFavoriteArticles().forEach(article -> {
            article.getFavoriteUsers().removeIf(user1 -> user1.getId() == userId);
            articleDao.save(article);
        });
        userDao.delete(user.get());
    }

    @Override
    public User follow(Long userId, Long followId) {

        Optional<User> current = userDao.findById(userId);

        if (current.isEmpty()) {
            new ResourceNotFoundException(User.class, "Id", String.valueOf(userId));
        }
        Optional<User> following = userDao.findById(followId);
        if (following.isEmpty()) {
            new ResourceNotFoundException(User.class, "Id", String.valueOf(followId));
        }
        current.get().getFollowings().add(following.get());
        userDao.update(current.get());
        return current.get();
    }

    @Override
    public User unFollow(Long userId, Long followId) {
        Optional<User> current = userDao.findById(userId);
        if (current.isEmpty()) {
            new ResourceNotFoundException(User.class, "Id", String.valueOf(userId));
        }
        Optional<User> following = userDao.findById(followId);
        if (following.isEmpty()) {
            new ResourceNotFoundException(User.class, "Id", String.valueOf(followId));
        }
        current.get().getFollowings().removeIf((c) -> c.getId() == followId);
        userDao.update(current.get());
        return current.get();
    }



}

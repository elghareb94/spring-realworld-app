package io.spring.service;

import io.spring.entity.User;

/**
 * @author Elghareb
 */
public interface UserService {

    User findById(Long userId);

    User findByUserName(String username);

    User findByEmail(String email);

    User update(User user);

    User register(User user);

    User follow(Long userId, Long followId);

    User unFollow(Long userId, Long followId);

    void delete(Long userId);
}

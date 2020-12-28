package io.spring.dao;

import io.spring.entity.User;

import java.util.Optional;

public interface UserDao {
    void save(User user);

    void update(User user);

    void delete(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}


package io.spring.service;

import io.spring.entity.Tag;

import java.util.List;

/**
 * @author Elghareb
 */
public interface TagService {
    List<Tag> findAll();
}

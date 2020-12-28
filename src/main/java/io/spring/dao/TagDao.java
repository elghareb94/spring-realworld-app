package io.spring.dao;

import io.spring.entity.Tag;

import java.util.List;

public interface TagDao {
    List<Tag> findAll();
}

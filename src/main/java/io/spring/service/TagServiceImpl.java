package io.spring.service;

import io.spring.dao.TagDao;
import io.spring.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Elghareb
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;

    public List<Tag> findAll() {
        return tagDao.findAll();
    }
}

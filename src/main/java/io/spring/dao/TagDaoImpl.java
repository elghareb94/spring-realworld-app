package io.spring.dao;

import io.spring.entity.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public class TagDaoImpl implements TagDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Tag> findAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        List<Tag> tags = currentSession.createQuery("from Tag").getResultList();
        return tags;
    }

}

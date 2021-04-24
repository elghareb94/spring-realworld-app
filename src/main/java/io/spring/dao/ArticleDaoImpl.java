package io.spring.dao;

import io.spring.entity.Article;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ArticleDaoImpl implements ArticleDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Article article) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(article);
    }

    @Override
    public void update(Article article) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(article);
    }

    @Override
    public void delete(Article article) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.delete(article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Article article = currentSession.get(Article.class, id);
        return Optional.ofNullable(article);
    }

    @Override
    public Optional<Article> findBySlug(String slug) {
        Query<Article> query = sessionFactory.getCurrentSession().createQuery("from Article a where a.slug like :slug", Article.class);
        query.setParameter("slug", slug);
        Article article = query.getSingleResult();
        return Optional.ofNullable(article);
    }

    @Override
    public List<Article> findAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        List<Article> articles = currentSession.createQuery("from Article").getResultList();
        return articles;
    }

    @Override
    public List<Article> findByAuthors(List<Long> ids) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Article> query = currentSession.createQuery("from Article a where a.author.id in :ids");
        query.setParameter("ids", ids);
        List<Article> articles = query.getResultList();
        return articles;
    }

}

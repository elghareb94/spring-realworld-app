package io.spring.service.impl;

import io.spring.dao.ArticleDao;
import io.spring.dao.CommentDao;
import io.spring.dao.UserDao;
import io.spring.entity.Article;
import io.spring.entity.Comment;
import io.spring.exception.exceptions.NoAuthorizationException;
import io.spring.exception.exceptions.ResourceNotFoundException;
import io.spring.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Elghareb
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Comment> getCommentsByArticleId(Long articleId) {
        Optional<Article> article = articleDao.findById(articleId);
        if (article.isEmpty()) {
            throw new ResourceNotFoundException(Article.class, "Id", String.valueOf(articleId));
        }
        return new ArrayList<>(articleDao.findById(articleId).get().getComments());
    }


    @Override
    public Comment addComment(Long articleId, Comment comment) {
        Optional<Article> article = articleDao.findById(articleId);
        if (article.isEmpty()) {
            throw new ResourceNotFoundException(Article.class, "Id", String.valueOf(articleId));
        }
        comment.setArticle(article.get());
        commentDao.save(comment);
        return comment;
    }

    @Override
    public void deleteComment(Long articleId, Long userId, Long commentId) {
        Optional<Comment> comment = commentDao.findById(commentId);

        if (comment.isEmpty()) {
            throw new ResourceNotFoundException(Comment.class, "Id", String.valueOf(commentId));
        }

        if (!comment.get().getAuthor().getId().equals(userId)) {
            throw new NoAuthorizationException();
        }
        commentDao.delete(comment.get());
    }

}

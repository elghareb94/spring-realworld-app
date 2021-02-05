package io.spring.api;


import io.spring.entity.Article;
import io.spring.entity.User;
import io.spring.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/articles/{articleId}/favorite")
public class FavoriteArticleApi {

    @Autowired
    private ArticleService articleService;


    @PostMapping
    private ResponseEntity favorite(@PathVariable Long articleId) {
        User user = User.builder().id(2L).build();

        Article article = articleService.addToFavorite(articleId, user);
        return ResponseEntity.ok(new HashMap() {
            {
                put("article", article);
                put("favorite", article.getFavoriteUsers());
            }
        });
    }

    @DeleteMapping
    private ResponseEntity unFavorite(@PathVariable Long articleId) {
        User user = User.builder().id(2L).build();

        Article article = articleService.removeFavorite(articleId, user);
        return ResponseEntity.ok(new HashMap() {{
            put("article", article);
            put("favorite", article.getFavoriteUsers());
        }});
    }

}

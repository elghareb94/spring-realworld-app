package io.spring.api;

import io.spring.dto.ArticleDTO;
import io.spring.entity.Article;
import io.spring.entity.User;
import io.spring.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping(path = "/articles")
public class ArticleApi {

    @Autowired
    private ArticleService articleService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll(@AuthenticationPrincipal User user) {
        System.out.println(user);
        return this.formatResponse("articles", articleService.getAll());
    }

    //Authentication required
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody ArticleDTO newArticleDTO, @AuthenticationPrincipal User user) {
        Article newArticle = Article.builder()
                .author(user)
                .body(newArticleDTO.getBody())
                .title(newArticleDTO.getTitle())
                .description(newArticleDTO.getDescription())
                .build();

        Article article = articleService.create(newArticle);
        return formatResponse("article", article);
    }

    //Authentication required
    @GetMapping("/feed/{id}")
    public ResponseEntity<?> getFeed(@PathVariable Long id, @AuthenticationPrincipal User user) {

        return this.formatResponse("articles", articleService.getFeed(user.getId()));
    }


    @GetMapping("/{articleId}")
    public Article get(@PathVariable Long articleId, @AuthenticationPrincipal UserDetails user) {

        System.out.println(user);
        return articleService.findById(articleId);
    }


    //Authentication required
    @PutMapping("/{articleId}")
    public ResponseEntity<?> update(@Valid @RequestBody ArticleDTO newArticleDTO, @PathVariable Long articleId, @AuthenticationPrincipal User user) {
        Article newArticle = Article.builder()
                .body(newArticleDTO.getBody())
                .title(newArticleDTO.getTitle())
                .description(newArticleDTO.getDescription())
                .build();

        newArticle = articleService.update(newArticle, user.getId());
        return formatResponse("article", newArticle);
    }

    //Authentication required
    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> delete(@PathVariable Long articleId, @AuthenticationPrincipal User user) {

        articleService.delete(articleId, user.getId());

        return ResponseEntity.ok(HttpHeaders.ACCEPT);
    }

    ResponseEntity<?> formatResponse(String key, Object value) {
        return ResponseEntity.ok(new HashMap<String, Object>() {
            {
                put(key, value);
            }
        });
    }
}
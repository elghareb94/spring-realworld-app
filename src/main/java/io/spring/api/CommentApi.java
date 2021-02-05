package io.spring.api;


import io.spring.dto.CommentDTO;
import io.spring.entity.Comment;
import io.spring.entity.User;
import io.spring.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/articles/{articleId}/comments")
public class CommentApi {

    @Autowired
    private CommentService commentService;

    @GetMapping()
    private ResponseEntity getComments(@PathVariable Long articleId) {
        return formatResponse("comments", commentService.getCommentsByArticleId(articleId));
    }

    @DeleteMapping(path = "/{commentId}")
    private void delete(@PathVariable Long articleId, @PathVariable Long commentId) {
        User user = User.builder().id(2L).build();
        commentService.deleteComment(articleId, user.getId(), commentId);
    }

    @PostMapping
    private ResponseEntity add(@PathVariable Long articleId, @RequestBody CommentDTO commentDTO) {
        User user = User.builder().id(2L).build();
        Comment comment = Comment.builder()
                .author(user)
                .body(commentDTO.getBody())
                .build();

        comment = commentService.addComment(articleId, comment);
        return formatResponse("comment", comment);
    }

    ResponseEntity formatResponse(String key, Object value) {
        return ResponseEntity.ok(new HashMap<String, Object>() {
            {
                put(key, value);
            }
        });
    }
}

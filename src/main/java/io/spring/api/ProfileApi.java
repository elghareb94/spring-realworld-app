package io.spring.api;


import io.spring.entity.User;
import io.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/profiles/{userId}")
public class ProfileApi {

    @Autowired
    private UserService userService;

    @GetMapping(path = "")
    private User get(@PathVariable Long userId) {
        User current = userService.findById(userId);
        return current;
    }

    @PostMapping(path = "/follow/{followId}")
    private ResponseEntity follow(@PathVariable Long userId, @PathVariable Long followId) {
        User user = userService.follow(userId, followId);
        return ResponseEntity.ok(new HashMap<String, Object>() {
            {
                put("following", user.getFollowings());
                put("followers", user.getFollowers());
            }
        });
    }

    @DeleteMapping(path = "/follow/{followId}")
    private ResponseEntity unfollow(@PathVariable Long userId, @PathVariable Long followId) {
        User user = userService.unFollow(userId, followId);
        return ResponseEntity.ok(new HashMap<String, Object>() {
            {
                put("following", user.getFollowings());
                put("followers", user.getFollowers());
            }
        });
    }

}



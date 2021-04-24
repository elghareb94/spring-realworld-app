package io.spring.api;


import io.spring.dto.UserDTO;
import io.spring.entity.User;
import io.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users")
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public User getUserProfile(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping(path = "/{userId}")
    public User get(@PathVariable String userId) {
        return userService.findByUserName(userId);
    }

    // /auth/register
    @PostMapping
    public User register(@RequestBody UserDTO userDTO) {

        User user = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();

        return userService.register(user);
    }
    @DeleteMapping(path = "/{userId}")
    public String delete(@PathVariable Long userId) {
        userService.delete(userId);
        return "DELETED";
    }

    @PutMapping
    public User update(@Valid @RequestBody UserDTO userDTO) {

        User user = User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .build();

        return userService.update(user);
    }
}

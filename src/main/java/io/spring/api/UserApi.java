package io.spring.api;


import io.spring.dto.UserDTO;
import io.spring.entity.User;
import io.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users")
public class UserApi {

    @Autowired
    private UserService userService;


    @GetMapping(path = "/{userId}")
    public User get(@PathVariable Long userId) {
        return userService.findById(userId);
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

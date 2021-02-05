package io.spring.api;

import io.spring.dto.LoginDTO;
import io.spring.dto.UserDTO;
import io.spring.entity.User;
import io.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthApi {

    @Autowired
    private UserService userService;

    // /auth/login
    @PostMapping(path = "/login")
    public User login(@RequestBody LoginDTO loginDTO) {
        System.out.println("login" + loginDTO);

        return User.builder().email(loginDTO.getEmail()).password(loginDTO.getPassword()).build();
    }


    // /auth/register

    @PostMapping(path = "/register")
    public User register(@RequestBody UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .build();
        return userService.register(user);
    }
}

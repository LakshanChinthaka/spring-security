package com.chinthaka.springsecurity.user;

import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return this.userService.createUser(user);
    }
    @GetMapping("/secured")
    public String secure(){
        return "Secured endpoint";
    }

    @GetMapping("/public")
    public String publicEndpoint(){
        return "public endpoint";
    }
}

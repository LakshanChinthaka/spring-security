package com.chinthaka.springsecurity.user;

import com.chinthaka.springsecurity.config.AppUser;
import com.chinthaka.springsecurity.config.LoggedinUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/secured-2")
    public Object secure2(@LoggedinUser AppUser appUser){
//      we can return only user details like that
//      return appUser.getUser();
        return appUser;
    }


    @GetMapping("/secured-admin")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String secureAdmin(){
        return "Only can see admin";
    }


    @GetMapping("/public")
    public String publicEndpoint(){
        return "public endpoint";
    }
}

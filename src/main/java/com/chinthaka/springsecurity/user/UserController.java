package com.chinthaka.springsecurity.user;

import com.chinthaka.springsecurity.config.AppUser;
import com.chinthaka.springsecurity.config.LoggedinUser;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public String secure() {
        return "Secured endpoint";
    }

    @GetMapping("/secured-2")
    public Object secure2(@LoggedinUser AppUser appUser) {
//      we can return only user details like that
//      return appUser.getUser();
        return appUser;
    }

    @GetMapping("/secured-admin")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String secureAdmin() {
        return "Only can see admin";
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        return "public endpoint";
    }

    //  user details can be updated by same {id} user or admin only
    //   we can do Two way (1)
//    @PutMapping("/update/{id}")
//    @PreAuthorize("#id == principal.user.id")
//    public User updateUser(@RequestBody User user, @PathVariable(name = "id") long id) {
//        return this.userService.updateUser(user, id);
//    }

//(2)--------------------------------
//        @PreAuthorize("#id == #appUser.user.id")
//        public User updateUser(
//            @RequestBody User user,
//            @PathVariable(name = "id") long id, @LoggedinUser AppUser appUser){
//            return this.userService.updateUser(user, id);
//        }

//    ----------------------------------------
//    Users are not allowed to update their details until 24 hours after updating the user details
//    we call to canUpdate method with params in userAuthorizationService class using @ sign
    @PutMapping("/update/{id}")
    @PreAuthorize("@userAuthorizationService.canUpdate(principal.user.id, #id) OR hasRole('ROLE_admin')" )
    public User updateUser(@RequestBody User user, @PathVariable(name = "id") long id) {
        return this.userService.updateUser(user, id);
    }
}

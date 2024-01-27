package com.chinthaka.springsecurity.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User createUser(User user) {
        String encodePassword = passwordEncode(user.getPassword());
        user.setPassword(encodePassword);
        return this.userRepo.save(user);
    }

    private String passwordEncode(String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public User updateUser(User user, long id) {
        User user1 = userRepo.getById(id);
        user1.setDisplayName(user.getDisplayName());
        user1.setLastUpdate(LocalDateTime.now());
        return userRepo.save(user1);
    }
}

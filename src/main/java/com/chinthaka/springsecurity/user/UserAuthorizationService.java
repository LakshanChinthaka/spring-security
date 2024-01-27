package com.chinthaka.springsecurity.user;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserAuthorizationService {

    private final UserRepo userRepo;

    public UserAuthorizationService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean canUpdate(long loggedUsrId, long pathUserId) {
        if (loggedUsrId != pathUserId) {
            return false;
        }
        Optional<User> optionalUser = userRepo.findById(pathUserId);
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();
//       local time - 24h for,  Users are not allowed to update their details until 24 hours after updating the user details
        LocalDateTime twentyFourHourAgo = LocalDateTime.now().minusHours(24);
        if (user.getLastUpdate() != null && user.getLastUpdate().isAfter(twentyFourHourAgo)) {
            return false;
        }
        return true;
    }
}

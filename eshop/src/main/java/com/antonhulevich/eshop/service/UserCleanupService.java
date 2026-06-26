package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dao.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserCleanupService {

    private final UserRepository userRepository;

    public UserCleanupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRate = 3600000)
    public void cleanup() {
        LocalDateTime threshold = LocalDateTime.now().minusHours(1);
        userRepository.deleteUnconfirmedUsers(threshold);
        System.out.println("Cleanup task: non-activated users removed at " + LocalDateTime.now());
    }
}
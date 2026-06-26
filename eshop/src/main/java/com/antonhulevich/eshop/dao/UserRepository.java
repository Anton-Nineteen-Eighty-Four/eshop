package com.antonhulevich.eshop.dao;

import com.antonhulevich.eshop.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);
    User findFirstByActivateCode(String activateCode);
    User findFirstByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.activateCode IS NOT NULL AND u.createdAt < :timeout")
    void deleteUnconfirmedUsers(LocalDateTime timeout);
}

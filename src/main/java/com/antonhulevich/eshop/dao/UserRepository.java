package com.antonhulevich.eshop.dao;

import com.antonhulevich.eshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);
    User findFirstByActivateCode(String activateCode);
    User findFirstByEmail(String email);
}

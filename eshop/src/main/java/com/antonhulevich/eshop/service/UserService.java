package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.domain.Bucket;
import com.antonhulevich.eshop.domain.User;
import com.antonhulevich.eshop.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService { //security
    boolean save(UserDto userDto);
    List<UserDto> getAll();
    UserDto findByName(String name);
    User getUserByName(String name);
    void updateProfile(UserDto userDto, String currentUsername);
    boolean activateUser(String activateCode);
    void updateRoleToManager(Long id);
    void assignBucketToUser(String username, Bucket bucket);
}

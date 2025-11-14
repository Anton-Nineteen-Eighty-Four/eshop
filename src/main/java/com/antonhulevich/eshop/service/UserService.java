package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.domain.User;
import com.antonhulevich.eshop.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService { //security
    boolean save(UserDto userDto);
    List<UserDto> getAll();
    User findByName(String name);
    void updateProfile(UserDto userDto);
}

package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService { //security
    boolean save(UserDto userDto);
}

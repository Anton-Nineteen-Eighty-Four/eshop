package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dao.UserRepository;
import com.antonhulevich.eshop.domain.Role;
import com.antonhulevich.eshop.domain.User;
import com.antonhulevich.eshop.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(UserDto userDto) {
        if(!userDto.getPassword().equals(userDto.getMatchingPassword())){
            throw new RuntimeException("Password is not equals");
        }
        User user = User.builder()
                .name(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .role(Role.ROLE_CLIENT)
                .build();

        userRepository.save(user);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if(user == null){
            throw new UsernameNotFoundException("User Not Found With Name: " + username);
        }
        List<GrantedAuthority> roles = new ArrayList<>();

        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),roles);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> usersList = userRepository.findAll();
        List<UserDto> usersDtoList = usersList.stream().map(this::toDto).collect(Collectors.toList());
        return usersDtoList;
    }

    private UserDto toDto(User user){
        return UserDto.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
    }
}

package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dao.UserRepository;
import com.antonhulevich.eshop.domain.Role;
import com.antonhulevich.eshop.domain.User;
import com.antonhulevich.eshop.dto.UserDto;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
//        User user = User.builder()
//                .name(userDto.getUsername())
//                .password(passwordEncoder.encode(userDto.getPassword()))
//                .email(userDto.getEmail())
//                .role(Role.ROLE_CLIENT)
//                .build();

        User user = new User();
        user.setName(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRole(Role.ROLE_CLIENT);

        userRepository.save(user);

        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
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
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getName());
        userDto.setEmail(user.getEmail());

//        return UserDto.builder()
//                .username(user.getName())
//                .email(user.getEmail())
//                .build();
        return userDto;
    }

    @Override
    public User findByName(String name) {
        return userRepository.findFirstByName(name);
    }

    @Override
    @Transactional
    public void updateProfile(UserDto userDto) {
        User savedUser = userRepository.findFirstByName(userDto.getUsername());
        if(savedUser == null){
            throw new RuntimeException("User with name " + userDto.getUsername() + "not foud");
        }

        boolean isChanged = false;

        if(userDto.getPassword() != null && !userDto.getPassword().isEmpty()){
            savedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            isChanged = true;
        }

        if(!Objects.equals(savedUser.getEmail(), userDto.getEmail())) {
            savedUser.setEmail(userDto.getEmail());
            isChanged = true;
        }

        if (isChanged) {
            userRepository.save(savedUser);
        }
    }
}

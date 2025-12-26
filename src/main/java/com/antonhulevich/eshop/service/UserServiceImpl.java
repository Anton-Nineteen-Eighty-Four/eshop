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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, MailSenderService mailSenderService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    @Transactional
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
        user.setActivateCode(UUID.randomUUID().toString());

        this.save(user);

        return true;
    }

    @Override
    @Transactional
    public void save(User user) {
        if (userRepository.findFirstByName(user.getName()) != null) {
            throw new RuntimeException("User with name " + user.getName() + " already exists");
        }

        if (userRepository.findFirstByEmail(user.getEmail()) != null) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }

        userRepository.save(user);

        if(user.getActivateCode() != null && !user.getActivateCode().isEmpty()){
            mailSenderService.sendActivateCode(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if(user == null){
            throw new UsernameNotFoundException("User Not Found With Name: " + username);
        }
        List<GrantedAuthority> roles = new ArrayList<>();

        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        boolean isEnabled = (user.getActivateCode() == null);

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                isEnabled,
                true,
                true,
                true,
                roles);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> usersList = userRepository.findAll();
        List<UserDto> usersDtoList = usersList.stream().map(this::toDto).collect(Collectors.toList());
        return usersDtoList;
    }

    private UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(Role.valueOf(user.getRole().name()));

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

        if (isChanged) {
            userRepository.save(savedUser);
        }
    }

    @Override
    @Transactional
    public boolean activateUser(String activateCode) {
        if(activateCode == null || activateCode.isEmpty()){
            return false;
        }
        User user = userRepository.findFirstByActivateCode(activateCode);
        if(user == null){
            return false;
        }

        user.setActivateCode(null);
        userRepository.save(user);

        return true;
    }

    @Override
    @Transactional
    public void updateRoleToManager(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ROLE_MANAGER) {
            user.setRole(Role.ROLE_CLIENT);
        } else {
            user.setRole(Role.ROLE_MANAGER);
        }

        userRepository.save(user);
    }
}

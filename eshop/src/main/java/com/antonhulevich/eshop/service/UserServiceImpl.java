package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dao.UserRepository;
import com.antonhulevich.eshop.domain.Bucket;
import com.antonhulevich.eshop.domain.Role;
import com.antonhulevich.eshop.domain.User;
import com.antonhulevich.eshop.dto.UserDto;
import com.antonhulevich.eshop.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final UserMapper userMapper;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, MailSenderService mailSenderService, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public boolean save(UserDto userDto) {
        if(!userDto.getPassword().equals(userDto.getMatchingPassword())){
            throw new RuntimeException("Password is not equals");
        }
        if (userRepository.findFirstByName(userDto.getName()) != null) {
            throw new RuntimeException("User with name " + userDto.getName() + " already exists");
        }
        if (userRepository.findFirstByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("User with email " + userDto.getEmail() + " already exists");
        }

        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.ROLE_CLIENT);
        user.setActivateCode(UUID.randomUUID().toString());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        if(user.getActivateCode() != null && !user.getActivateCode().isEmpty()){
            mailSenderService.sendActivateCode(user);
        }

        return true;
    }

    @Override
    @Transactional
    public void assignBucketToUser(String username, Bucket bucket) {
        User user = userRepository.findFirstByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found With Name: " + username);
        }
        user.setBucked(bucket);
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
        return userMapper.toDtoList(usersList);
    }

    @Override
    @Transactional
    public UserDto findByName(String name) {
        User user = userRepository.findFirstByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found With Name: " + name);
        }
        return userMapper.toDto(user);
    }
    @Override
    public User getUserByName(String name) {
        return userRepository.findFirstByName(name);
    }

    @Override
    @Transactional
    public void updateProfile(UserDto userDto, String currentUsername) {
        if (!Objects.equals(currentUsername, userDto.getName())) {
            throw new AccessDeniedException("You cannot change the user name");
        }

        User savedUser = userRepository.findFirstByName(userDto.getName());

        if(savedUser == null){
            throw new RuntimeException("User with name " + userDto.getName() + "not foud");
        }

        if (!Objects.equals(savedUser.getEmail(), userDto.getEmail())) {
            throw new IllegalArgumentException("You cannot change the email");
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

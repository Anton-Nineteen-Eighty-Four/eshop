package com.antonhulevich.eshop.controller;

import com.antonhulevich.eshop.domain.User;
import com.antonhulevich.eshop.dto.UserDto;
import com.antonhulevich.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user", new UserDto());
        return "user";
    }

    @PostMapping("/new")
    public String saveUser(UserDto userDto, Model model){
        if (userService.save(userDto)) {
            return "redirect:/users";
        } else {
            model.addAttribute(userDto);
            return "user";
        }
    }

    @GetMapping
    public String usersList(Model model){
        List<UserDto> userDtoList = userService.getAll();
        model.addAttribute("users", userDtoList);
        return "userList";
    }

    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal){
        if(principal == null){
            throw new RuntimeException("Yuo are not authorize");
        }
        User user = userService.findByName(principal.getName());

        UserDto userDto = UserDto.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
        model.addAttribute("user", userDto);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfileUser(UserDto userDto, Model model, Principal principal){
        if(principal == null || !Objects.equals(principal.getName(), userDto.getUsername())){
            throw new RuntimeException("Yuo are not authorized");
        }
        if(userDto.getPassword() != null &&
                !userDto.getPassword().isEmpty() &&
                !Objects.equals(userDto.getPassword(), userDto.getMatchingPassword())){

            model.addAttribute("user",userDto);
            //нужно добавить сообщение, сделать это потом
            return "/profile";
        }
        userService.updateProfile(userDto);
        return "redirect:/users/profile";
    }

}

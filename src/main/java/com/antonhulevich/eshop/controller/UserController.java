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

import java.util.List;

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

}

package com.antonhulevich.eshop.controller;

import com.antonhulevich.eshop.dto.UserDto;
import com.antonhulevich.eshop.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user", new UserDto());
        return "user";
    }

    @PreAuthorize("#userDto.name == authentication.name")
    @GetMapping("/{name}/roles")
    @ResponseBody
    public String getRoles(@PathVariable("name") String username){
        UserDto byName = userService.findByName(username);
        return byName.getRole().name();
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String usersList(Model model){
        List<UserDto> userDtoList = userService.getAll();
        model.addAttribute("users", userDtoList);
        return "userList";
    }

    @GetMapping("/profile")
    public String profileUser(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser == null) {
            return "redirect:/login";
        }
        UserDto userDto = userService.findByName(currentUser.getUsername());

        model.addAttribute("user", userDto);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfileUser(UserDto userDto, Model model, @AuthenticationPrincipal UserDetails currentUser){
        if (currentUser == null) {
            return "redirect:/login";
        }
        try {
            userService.updateProfile(userDto, currentUser.getUsername());
        } catch (IllegalArgumentException | AccessDeniedException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", userDto);
        }

        return "profile";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(Model model, @PathVariable("code") String activateCode){
        boolean activated = userService.activateUser(activateCode);
        model.addAttribute("activated", activated);
        return "activate-user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign-manager/{id}")
    public String assignManagerRole(@PathVariable Long id) {
        userService.updateRoleToManager(id);
        return "redirect:/users";
    }
}

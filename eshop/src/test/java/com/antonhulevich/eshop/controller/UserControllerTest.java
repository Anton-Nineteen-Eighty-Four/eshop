package com.antonhulevich.eshop.controller;

import com.antonhulevich.eshop.config.SecurityConfig;
import com.antonhulevich.eshop.domain.Role;
import com.antonhulevich.eshop.domain.User;
import com.antonhulevich.eshop.dto.UserDto;
import com.antonhulevich.eshop.mapper.UserMapper;
import com.antonhulevich.eshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import({SecurityConfig.class})
class UserControllerTest {

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    public User clientUser = new User(1l,"testuser", Role.ROLE_CLIENT);

    @BeforeEach
    void setUp() {
        UserDto clientUserDto = org.mapstruct.factory.Mappers.getMapper(UserMapper.class).toDto(clientUser);

        Mockito.when(userService.findByName(Mockito.eq(clientUser.getName())))
                .thenReturn(clientUserDto); // Возвращаем именно DTO!
    }

    @Test
    void getRolesNotAuthorized() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/users/testuser/roles"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "testuser")
    @Test
    void getRoles() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/users/testuser/roles"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("ROLE_CLIENT"));
    }

    @WithMockUser(username = "otheruser")
    @Test
    void getRolesWrongUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/testuser/roles"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void newUserNotAuthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/new"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "testuser", roles = {"CLIENT"})
    @Test
    void newUserNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/new"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void newUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/new"))
                .andExpect(status().isOk());
    }
}
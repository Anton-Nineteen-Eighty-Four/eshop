package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dao.UserRepository;
import com.antonhulevich.eshop.domain.User;
import com.antonhulevich.eshop.dto.UserDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class UserServiceImplTest {
    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @BeforeAll
    static void beforeAll(){
        System.out.println("Before All tests");
    }

    @BeforeEach
    void setUp(){
        System.out.println("Before each test");
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userRepository = Mockito.mock(UserRepository.class);

        userService = new UserServiceImpl(passwordEncoder,userRepository);
    }

    @AfterEach
    void afterEach(){
        System.out.println("After each test");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After All test");
    }

    @Test
    void  checkFindByName(){
        //have
        String name = "Bill";
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName(name);

        Mockito.when(userRepository.findFirstByName(Mockito.anyString())).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findByName(name);

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser,actualUser);

    }

    @Test
    void checkFindByNameExacted(){
        //have
        String name = "Bill";
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName(name);

        Mockito.when(userRepository.findFirstByName(Mockito.eq(name))).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findByName(name);
        User randomUser = userService.findByName(UUID.randomUUID().toString());

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser,actualUser);

        Assertions.assertNull(randomUser);

    }

    @Test
    void checkSaveIncorrectPassword(){
        //have
        UserDto userDto = new UserDto();
        userDto.setPassword("pass");
        userDto.setMatchingPassword("another");

        //execute
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.save(userDto);
            }
        });
    }

    @Test
    void checkSave(){
        //have
        UserDto userDto = new UserDto();
        userDto.setUsername("name");
        userDto.setEmail("email");
        userDto.setPassword("pass");
        userDto.setMatchingPassword("pass");

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("pass");

        //execute
        boolean result = userService.save(userDto);

        //check
        Assertions.assertTrue(result);
        Mockito.verify(passwordEncoder).encode(Mockito.anyString());
        Mockito.verify(userRepository).save(Mockito.any());
    }


}

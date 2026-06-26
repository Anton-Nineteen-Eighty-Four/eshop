package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dao.UserRepository;
import com.antonhulevich.eshop.domain.User;
import com.antonhulevich.eshop.dto.UserDto;
import com.antonhulevich.eshop.mapper.UserMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class UserServiceImplTest {
    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private MailSenderService mailSenderService;
    private UserMapper userMapper;

    @BeforeAll
    static void beforeAll(){
        System.out.println("Before All tests");
    }

    @BeforeEach
    void setUp(){
        System.out.println("Before each test");
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userRepository = Mockito.mock(UserRepository.class);
        mailSenderService = Mockito.mock(MailSenderService.class);
        userMapper = org.mapstruct.factory.Mappers.getMapper(UserMapper.class);

        userService = new UserServiceImpl(passwordEncoder, userRepository, mailSenderService, userMapper);
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
    void checkFindByName(){
        //have
        String name = "Bill";
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName(name);

        Mockito.when(userRepository.findFirstByName(Mockito.anyString())).thenReturn(expectedUser);

        //execute
        UserDto actualUser = userService.findByName(name);

        //check
        Assertions.assertNotNull(actualUser);
        // Сравниваем точечно поля DTO и Entity, так как это разные классы
        Assertions.assertEquals(expectedUser.getId(), actualUser.getId());
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
    }

    @Test
    void checkFindByNameExacted(){
        //have
        String name = "Bill";
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName(name);

        Mockito.when(userRepository.findFirstByName(Mockito.eq(name))).thenReturn(expectedUser);
        Mockito.when(userRepository.findFirstByName(Mockito.argThat(arg -> !name.equals(arg)))).thenReturn(null);

        //execute
        UserDto actualUser = userService.findByName(name);

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser.getId(), actualUser.getId());
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());

        // Метод findByName теперь бросает исключение, если юзер не найден
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userService.findByName(UUID.randomUUID().toString());
        });
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
        userDto.setName("name");
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

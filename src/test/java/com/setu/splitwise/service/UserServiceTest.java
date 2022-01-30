package com.setu.splitwise.service;

import com.setu.splitwise.model.User;
import com.setu.splitwise.repository.UserRepository;
import com.setu.splitwise.service.impl.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


/**
 * @author amankumar
 * Junit Test cases for User Service
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IUserService userService = new UserService();

    private static User user;

    @BeforeAll
    static void setUp() {
        user = User.builder().id(1L).name("Aman")
                .phoneNumber("987676789").email("aman.singl3000@gmail.com").build();
    }

    @DisplayName("Create User")
    @Test
    @Order(1)
    void testCreateUser() {
        user.setId(1L);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        Assertions.assertNotNull(userService.createUser(user));
    }

    @DisplayName("Get Created User ")
    @Test
    @Order(2)
    void testGetUserByID() {

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Assertions.assertNotNull(userService.getUser(1L));
    }

    @DisplayName("Update User ")
    @Test
    @Order(3)
    void testUpdateUser() {
        Mockito.when(userRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        Assertions.assertNotNull(userService.updateUser(User.builder()
                .name("Raman").email("aman@gmail.com").phoneNumber("78787987").build(), 1L));
    }
    @DisplayName("Delete User ")
    @Test
    @Order(4)
    void testDeleteUser() {

        Mockito.when(userRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).delete(Mockito.any());
        userService.deleteUser(1L);
    }

}
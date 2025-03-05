package com.userservice.service;

import com.userservice.dto.ConsumerDTO;
import com.userservice.entity.Consumer;
import com.userservice.enums.Role;
import com.userservice.exception.UserNotFoundException;
import com.userservice.repository.ConsumerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ConsumerServiceTest {
    @Mock
    private ConsumerRepository repository;

    @InjectMocks
    private ConsumerService consumerService;

    @InjectMocks
    private  UserDetailsServiceimpl userDetailsServiceimpl;
    private Consumer user;
    private ConsumerDTO userDto;

    @BeforeEach
    void setUp() {
        user = new Consumer();
        user.setId(1L);
        user.setUserName("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(Role.USER);

        userDto = new ConsumerDTO();
        userDto.setUserName("newuser");
        userDto.setEmail("new@example.com");
        userDto.setPassword("newpassword");
        userDto.setRole(Role.USER);
    }

    @Test
    void testSaveNewUser_Success() {
        when(repository.save(any(Consumer.class))).thenReturn(user);
        String response = consumerService.saveNewUser(userDto);
        assertEquals("User registered successfully", response);
    }

    @Test
    void testSaveNewUser_EmailAlreadyExists() {
        when(repository.save(any(Consumer.class))).thenThrow(DataIntegrityViolationException.class);
        String response = consumerService.saveNewUser(userDto);
        assertEquals("Email/Username already exists", response);
    }

    @Test
    void testUpdateUserDetails_Success() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("testuser");

        when(repository.findByUserName("testuser")).thenReturn(user);
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.existsByUserName(anyString())).thenReturn(false);
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.save(any(Consumer.class))).thenReturn(user);

        String response = consumerService.updateUserDetails(1L, userDto);
        assertEquals("User details updated successfully", response);
    }
    @Test
    void testUpdateUserDetails_Unauthorized() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("anotherUser");

        Consumer anotherUser = new Consumer();
        anotherUser.setId(2L);
        anotherUser.setUserName("anotherUser");

        when(repository.findByUserName("anotherUser")).thenReturn(anotherUser);
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        String response = consumerService.updateUserDetails(1L, userDto);
        assertEquals("Unauthorized: You can only update your own account", response);
    }


    @Test
    void testGetAllUsers() {
        when(repository.findAll()).thenReturn(Arrays.asList(user));
        List<Consumer> users = consumerService.getAll();
        assertThat(users).hasSize(1);
    }

    @Test
    void testFindById_UserExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        Optional<Consumer> foundUser = consumerService.findById(1L);
        assertTrue(foundUser.isPresent());
    }


    @Test
    void testFindById_UserNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        Optional<Consumer> foundUser = consumerService.findById(2L);
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testDeleteById_UserExists() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        assertDoesNotThrow(() -> consumerService.deleteById(1L));
    }

    @Test
    void testDeleteById_UserNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> consumerService.deleteById(1L));
    }

    @Test
    void testDeleteAllUsers() {
        doNothing().when(repository).deleteAll();
        String response = consumerService.deleteAll();
        assertEquals("All Users are deleted succesfully", response);
    }

    @Test
    void testFindByUserName() {
        when(repository.findByUserName("testuser")).thenReturn(user);
        Consumer foundUser = consumerService.findByUserName("testuser");
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUserName());
    }

    @Test
    void testGetCurrentUser_UserExists() {
        when(repository.findByUserName("testuser")).thenReturn(user);
        ConsumerDTO foundUser = consumerService.getCurrentUser("testuser");
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUserName());
    }

    @Test
    void testGetCurrentUser_UserNotFound() {
        when(repository.findByUserName("unknown")).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> consumerService.getCurrentUser("unknown"));
    }
}

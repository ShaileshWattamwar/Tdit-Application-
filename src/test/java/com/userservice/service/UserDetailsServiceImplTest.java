package com.userservice.service;

import com.userservice.entity.Consumer;
import com.userservice.enums.Role;
import com.userservice.repository.ConsumerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserDetailsServiceImplTest {
    //pr
    @Mock
    private ConsumerRepository consumerRepository;

    @InjectMocks
    private UserDetailsServiceimpl userDetailsService;

    private Consumer consumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consumer = new Consumer();
        consumer.setUserName("testuser");
        consumer.setPassword("password123");
        consumer.setRoles(Role.USER);
    }

    @Test
    void loadUserByUsername_UserFound() {

        when(consumerRepository.findByUserName("testuser")).thenReturn(consumer);


        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");


        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        verify(consumerRepository, times(1)).findByUserName("testuser");
    }

    @Test
    void loadUserByUsername_UserNotFound() {

        when(consumerRepository.findByUserName("testuser")).thenReturn(null);


        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("testuser");
        });
        verify(consumerRepository, times(1)).findByUserName("testuser");
    }
}

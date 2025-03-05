package com.userservice.controller;

import  java.util.List;
import java.util.Arrays;
import java.util.Optional;

import com.userservice.config.SecurityConfiguration;
import com.userservice.entity.Consumer;
import com.userservice.repository.ConsumerRepository;
import com.userservice.service.ConsumerService;
import com.userservice.service.UserDetailsServiceimpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(AdminController.class)
@Import(SecurityConfiguration.class)
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ConsumerService consumerService;
    @MockBean
    private UserDetailsServiceimpl userDetailsServiceimpl;

    @MockBean
    private ConsumerRepository consumerRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_Success() throws Exception {
        when(consumerService.getAll()).thenReturn(Arrays.asList(new Consumer()));

        mockMvc.perform(get("/admin/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUserById_Success() throws Exception {
        doNothing().when(consumerService).deleteById(1L);

        mockMvc.perform(delete("/admin/delete/1"))
                .andExpect(status().isNoContent());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteAll() throws Exception {

        when(consumerService.deleteAll()).thenReturn("All Users are deleted successfully");


        mockMvc.perform(delete("/admin/deleteAll"))
                .andExpect(status().isOk());


        verify(consumerService).deleteAll();
    }
    @Test
    void getUserById_WhenUserExists_ReturnsUser() throws Exception {

        Long userId = 1L;
        Consumer mockUser = new Consumer();
        mockUser.setId(userId);
        mockUser.setUserName("testuser");
        mockUser.setEmail("test@example.com");

        when(consumerService.findById(userId)).thenReturn(
                Optional.of(mockUser));


        mockMvc.perform(get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.userName").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
}


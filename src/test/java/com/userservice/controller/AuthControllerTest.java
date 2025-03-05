package com.userservice.controller;

import com.userservice.config.SecurityConfiguration;
import com.userservice.entity.Consumer;
import com.userservice.enums.Role;
import com.userservice.repository.ConsumerRepository;
import com.userservice.service.ConsumerService;
import com.userservice.service.TokenBlackList;
import com.userservice.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({SecurityConfiguration.class, JwtUtil.class})
@TestPropertySource(properties = "b4f63be9c1a8a2d4e3f7c8559a97bbf2bbdb178db82306fbb20d4d97d6226ae1\n")
public class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;

   @Autowired
    private  JwtUtil jwtUtil;
    @MockBean
    private ConsumerRepository consumerRepository;
    @MockBean
    private ConsumerService consumerService;
    @MockBean private AuthenticationManager authenticationManager;
    @MockBean private TokenBlackList tokenBlacklist;

    @Test
    @WithAnonymousUser
    void login_Success() throws Exception {

        Consumer user = new Consumer(1L, "testuser", "test@email.com", "password", Role.USER);
        when(consumerService.findByUserName("testuser")).thenReturn(user);


        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"testuser\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").exists());
    }

    @Test
    @WithMockUser
    void logout() throws Exception {

        Consumer testUser = new Consumer();
        testUser.setUserName("testuser");
        testUser.setPassword("encodedPassword");
        testUser.setRoles(Role.USER);
        when(consumerRepository.findByUserName("testuser")).thenReturn(testUser);


        String validToken = jwtUtil.generateToken("testuser");

        mockMvc.perform(post("/auth/logout")
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk());


        verify(tokenBlacklist).addToBlacklist(validToken);
    }
}



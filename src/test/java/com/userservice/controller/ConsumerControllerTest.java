package com.userservice.controller;

import com.userservice.config.SecurityConfiguration;
import com.userservice.dto.ConsumerDTO;
import com.userservice.enums.Role;
import com.userservice.repository.ConsumerRepository;
import com.userservice.service.ConsumerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ConsumerController.class)
@Import(SecurityConfiguration.class)
public class ConsumerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ConsumerService consumerService;

    @MockBean
    private ConsumerRepository consumerRepository;
//    @InjectMocks
//    private  ConsumerController consumerController;
@Test
@WithAnonymousUser
void registerUser_Success() throws Exception {
    when(consumerService.saveNewUser(any())).thenReturn("User registered successfully");

    String jsonPayload = """
        {
            "userName": "user",
            "email": "user@email.com",
            "password": "pass1234",
           "confirmPassword":"pass1234",
            "role": "USER"
        }
        """;

    mockMvc.perform(post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonPayload))
            .andExpect(status().isCreated());
}

    @Test
    @WithMockUser(username = "user",roles = "USER")
    void getCurrentUser_Success() throws Exception {
        ConsumerDTO dto = new ConsumerDTO();
        dto.setUserName("user");
        when(consumerService.getCurrentUser("user")).thenReturn(dto);

        mockMvc.perform(get("/user/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("user"));
    }
}

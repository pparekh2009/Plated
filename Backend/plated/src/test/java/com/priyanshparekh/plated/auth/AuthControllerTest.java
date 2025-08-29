package com.priyanshparekh.plated.auth;

import com.priyanshparekh.plated.config.TestSecurityConfig;
import com.priyanshparekh.plated.security.JwtAuthenticationFilter;
import com.priyanshparekh.plated.security.JwtService;
import com.priyanshparekh.plated.user.User;
import com.priyanshparekh.plated.user.dto.UserSignUpRequest;
import com.priyanshparekh.plated.user.dto.UserSignUpResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @Test
    public void UserSignUpTest() throws Exception {
        // Arrange
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest("test@email.com", "password", "John Doe", "Test bio", "Chef", "google.com");
        User savedUser = new User(1L, "test@email.com", "password", "John Doe", "Test bio", "Chef", "google.com");
        UserSignUpResponse savedUserSignUpResponse = new UserSignUpResponse(1L, "test@email.com", "John Doe", "Test bio", "Chef", "google.com");

        String jsonUser = """
                {
                    "email": "test@gmail.com",
                    "password": "password",
                    "firstName": "John",
                    "lastName": "Doe",
                    "bio": "Test Bio",
                    "profession": "Chef"
                }
                """;

        when(authService.signUp(any())).thenReturn(savedUserSignUpResponse);

        // Act + Assert

        mockMvc.perform(post("/api/v1/auth/signup")
                        .header("Content-Type", "application/json")
                        .content(jsonUser))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.displayName").value("John Doe"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

}

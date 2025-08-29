package com.priyanshparekh.plated.auth;

import com.priyanshparekh.plated.user.User;
import com.priyanshparekh.plated.user.UserMapper;
import com.priyanshparekh.plated.user.UserRepository;
import com.priyanshparekh.plated.user.dto.UserLoginRequest;
import com.priyanshparekh.plated.user.dto.UserLoginResponse;
import com.priyanshparekh.plated.user.dto.UserSignUpRequest;
import com.priyanshparekh.plated.user.dto.UserSignUpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Autowired
    private AuthService authService;

    @Test
    void userSignUpTest_ReturnsUser() {
        // Arrange
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest("test@email.com", "password", "John Doe", "Test bio", "Chef", "google.com");
        User mapped = new User(1L, "test@email.com", "password", "John Doe", "Test bio", "Chef", "google.com");
        UserSignUpResponse userSignUpResponse = new UserSignUpResponse(1L, "test@email.com", "John Doe", "Test bio", "Chef", "google.com");

        when(userMapper.toUser(userSignUpRequest)).thenReturn(mapped);
        when(userMapper.toDto(mapped)).thenReturn(userSignUpResponse);

        // act
        UserSignUpResponse signedUpUser = authService.signUp(userSignUpRequest);

        // assert
        assertNotNull(signedUpUser);
        assertEquals(userSignUpRequest.getEmail(), signedUpUser.getEmail());
    }
}

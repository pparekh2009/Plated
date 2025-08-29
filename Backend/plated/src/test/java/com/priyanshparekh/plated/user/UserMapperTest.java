package com.priyanshparekh.plated.user;

import com.priyanshparekh.plated.user.dto.UserSignUpRequest;
import com.priyanshparekh.plated.user.dto.UserSignUpResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void UserSignUpRequest_To_UserEntity_Test() {
        // Arrange
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest("abc@email.com", "password", "John Doe", "Test bio", "chef", "google.com");

        // Act
        User user = userMapper.toUser(userSignUpRequest);

        // Assert
        assertNotNull(user);
        assertEquals(user.getEmail(), userSignUpRequest.getEmail());
        assertEquals(user.getPassword(), userSignUpRequest.getPassword());
        assertEquals(user.getBio(), userSignUpRequest.getBio());
    }

    @Test
    public void UserEntity_To_UserSignUpResponse_Test() {
        // Arrange
        User user = new User(1L, "abc@email.com", "password", "John Doe", "Test bio", "chef", "google.com");

        // Act
        UserSignUpResponse userSignUpResponse = userMapper.toDto(user);

        // Assert
        assertNotNull(userSignUpResponse);
        assertEquals(userSignUpResponse.getId(), user.getId());
    }
}

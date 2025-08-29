package com.priyanshparekh.plated.auth;

import com.priyanshparekh.plated.user.dto.UserLoginRequest;
import com.priyanshparekh.plated.user.dto.UserLoginResponse;
import com.priyanshparekh.plated.user.dto.UserSignUpRequest;
import com.priyanshparekh.plated.user.dto.UserSignUpResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signUp(@RequestBody @Valid UserSignUpRequest user) {
        System.out.println("Controller hit");
        UserSignUpResponse signedUpUser = authService.signUp(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(signedUpUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        UserLoginResponse loggedInUser = authService.login(userLoginRequest);
        return ResponseEntity.ok(loggedInUser);
    }
}

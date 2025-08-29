package com.priyanshparekh.plated.user;

import com.priyanshparekh.plated.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class UserController {

    private final AuthService authService;

//    @PostMapping("/signup")
//    public ResponseEntity<User> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
//        User signUpUser = authService.signUp(userSignUpRequest);
//        return new ResponseEntity<>(signUpUser, HttpStatus.CREATED);
//    }

}

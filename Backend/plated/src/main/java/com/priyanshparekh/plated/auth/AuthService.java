package com.priyanshparekh.plated.auth;

import com.priyanshparekh.plated.security.JwtService;
import com.priyanshparekh.plated.security.PlatedUserDetails;
import com.priyanshparekh.plated.user.User;
import com.priyanshparekh.plated.user.UserMapper;
import com.priyanshparekh.plated.user.UserRepository;
import com.priyanshparekh.plated.user.dto.UserLoginRequest;
import com.priyanshparekh.plated.user.dto.UserLoginResponse;
import com.priyanshparekh.plated.user.dto.UserSignUpRequest;
import com.priyanshparekh.plated.user.dto.UserSignUpResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserSignUpResponse signUp(UserSignUpRequest userSignUpRequest) {
        System.out.println("Service Hit");
        log.info("authService: signUp: userSignUpRequest: website: {}", userSignUpRequest.getWebsite());
        User user = userMapper.toUser(userSignUpRequest);
        log.info("authService: signUp: user: website: {}", user.getWebsite());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("authService: signUp: savedUser: website: {}", savedUser.getWebsite());

        UserSignUpResponse userSignUpResponse = userMapper.toDto(savedUser);
        log.info("authService: signUp: userSignUpResponse: website: {}", userSignUpResponse.getWebsite());
        return userSignUpResponse;
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword())
        );

        User user = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("authService: login: user: {}", user.getId());

        String jwtToken = jwtService.generateToken(new PlatedUserDetails(user));
        log.info("authService: login: jwt token: {}", jwtToken);

        return UserLoginResponse.builder()
                .id(user.getId())
                .displayName(user.getDisplayName())
                .bio(user.getBio())
                .profession(user.getProfession())
                .website(user.getWebsite())
                .jwtToken(jwtToken)
                .build();
    }
}

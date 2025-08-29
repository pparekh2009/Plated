package com.priyanshparekh.plated.user.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{user-id}/profile")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable("user-id") Long userId) {
        UserProfileDto userProfileDto = profileService.getUserProfile(userId);
        return ResponseEntity.ok(userProfileDto);
    }

}

package com.priyanshparekh.plated.user.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignUpRequest {

    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email not valid")
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password must only contain alphanumeric characters")
    @Size(min = 8, max = 20, message = "Password must be between {min} and {max} characters")
    private String password;

    private String displayName;
    private String bio;
    private String profession;

    @Pattern(regexp = "^(https?:\\/\\/)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(\\/\\S*)?$|^$", message = "URL not valid")
    private String website;

}

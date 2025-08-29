package com.priyanshparekh.plated.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpResponse {

    private Long id;
    private String email;
    private String displayName;
    private String bio;
    private String profession;
    private String website;

}

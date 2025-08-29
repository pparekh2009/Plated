package com.priyanshparekh.plated.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {

    private Long id;
    private String displayName;
    private String bio;
    private String profession;
    private String website;
    private String jwtToken;

}

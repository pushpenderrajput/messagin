package com.pushpender.auth.dtos.AuthDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResDto {
    private Long userId;
    private String email;
    private String accessToken;
    private String refreshToken;
    private String role;
    private long accessTokenExpiresInMs;
}

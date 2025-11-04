package com.pushpender.auth.services;

import com.pushpender.auth.dtos.AuthDto.AuthResDto;
import com.pushpender.auth.dtos.AuthDto.LoginReqDto;
import com.pushpender.auth.entities.RefreshToken;
import com.pushpender.auth.entities.User;
import com.pushpender.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthResDto login(LoginReqDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!user.getIsActive()) {
            throw new RuntimeException("User account is disabled.");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtService.generateToken(user.getEmail());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return AuthResDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .accessTokenExpiresInMs(jwtService.getJwtExpiration())
                .build();
    }


    public AuthResDto refreshAccessToken(String refreshTokenStr) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenStr)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (!refreshTokenService.isValid(refreshToken)) {
            throw new RuntimeException("Refresh token expired or revoked");
        }

        User user = refreshToken.getUser();

        String newAccessToken = jwtService.generateToken(user.getEmail());

        return AuthResDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .accessToken(newAccessToken)
                .refreshToken(refreshTokenStr)
                .accessTokenExpiresInMs(jwtService.getJwtExpiration())
                .build();
    }


    public void logout(String refreshTokenStr) {
        refreshTokenService.findByToken(refreshTokenStr)
                .ifPresent(refreshTokenService::revoke);
    }
}

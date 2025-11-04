package com.pushpender.auth.services;

import com.pushpender.auth.entities.RefreshToken;
import com.pushpender.auth.entities.User;
import com.pushpender.auth.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    // value from application.properties
    @Value("${jwt.refresh-expiration-ms}")
    private long refreshTokenDurationMs;


    public RefreshToken createRefreshToken(User user) {
        String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUser(user);

        Instant expiryInstant = Instant.now().plusMillis(refreshTokenDurationMs);
        refreshToken.setExpiryDate(LocalDateTime.ofInstant(expiryInstant, ZoneId.systemDefault()));
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    public boolean isValid(RefreshToken token) {
        if (token == null) return false;
        if (token.isRevoked()) return false;
        return token.getExpiryDate().isAfter(LocalDateTime.now());
    }


    public void revoke(RefreshToken token) {
        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }


    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}

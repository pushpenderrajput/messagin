package com.pushpender.auth.repositories;

import com.pushpender.auth.entities.RefreshToken;
import com.pushpender.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}

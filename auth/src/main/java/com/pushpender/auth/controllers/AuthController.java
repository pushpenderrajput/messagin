package com.pushpender.auth.controllers;

import com.pushpender.auth.dtos.AuthDto.AuthResDto;
import com.pushpender.auth.dtos.AuthDto.LoginReqDto;
import com.pushpender.auth.dtos.UserDto.UserReqDto;
import com.pushpender.auth.dtos.UserDto.UserResDto;
import com.pushpender.auth.services.AuthService;
import com.pushpender.auth.services.JwtService;
import com.pushpender.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResDto> register(@RequestBody UserReqDto dto) {
        UserResDto response = userService.registerUser(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(@RequestBody LoginReqDto dto) {
        AuthResDto response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResDto> refresh(@RequestParam("refreshToken") String refreshToken) {
        AuthResDto response = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam("refreshToken") String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "valid", false,
                    "message", "Missing or malformed Authorization header"
            ));
        }

        String token = authHeader.substring(7);
        boolean valid = jwtService.isTokenValid(token);

        if (valid) {
            String email = jwtService.extractEmail(token);
            return ResponseEntity.ok(Map.of(
                    "valid", true,
                    "email", email,
                    "message", "Token is valid"
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "valid", false,
                    "message", "Invalid or expired token"
            ));
        }
    }




}

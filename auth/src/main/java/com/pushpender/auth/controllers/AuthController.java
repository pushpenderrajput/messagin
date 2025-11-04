package com.pushpender.auth.controllers;

import com.pushpender.auth.dtos.AuthDto.AuthResDto;
import com.pushpender.auth.dtos.AuthDto.LoginReqDto;
import com.pushpender.auth.dtos.UserDto.UserReqDto;
import com.pushpender.auth.dtos.UserDto.UserResDto;
import com.pushpender.auth.services.AuthService;
import com.pushpender.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

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


}

package com.pushpender.auth.services;

import com.pushpender.auth.dtos.UserDto.UserReqDto;
import com.pushpender.auth.dtos.UserDto.UserResDto;
import com.pushpender.auth.entities.User;
import com.pushpender.auth.repositories.RefreshTokenRepository;
import com.pushpender.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final RefreshTokenRepository tokenRepo;
    private final PasswordEncoder passwordEncoder;

    public UserResDto registerUser(UserReqDto dto){
        if(userRepo.existsByEmail(dto.getEmail())){
            throw new RuntimeException("Username Already used.");
        }
        User newUser = new User();
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setRole(dto.getRole() != null ? dto.getRole() : User.Role.USER);
        newUser.setIsActive(true);

        User saved = userRepo.save(newUser);
        UserResDto response = new UserResDto();
        response.setId(saved.getId());
        response.setEmail(saved.getEmail());
        response.setIsActive(saved.getIsActive());
        response.setCreatedAt(saved.getCreatedAt());
        response.setRole(saved.getRole());
        return response;
    }

    public User findByEmail(String email){
        User user = userRepo.findByEmail(email).orElse(null);
        if(user == null){
            throw new RuntimeException("User not found with username:"+ email);
        }
        return user;
    }

    public Boolean existsByEmail(String email){
        return userRepo.existsByEmail(email);
    }

}

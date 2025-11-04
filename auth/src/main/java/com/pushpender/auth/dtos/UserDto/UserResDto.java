package com.pushpender.auth.dtos.UserDto;

import com.pushpender.auth.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResDto {
    private Long id;
    private String email;
    private User.Role role;
    private LocalDateTime createdAt;
    private Boolean isActive;
}

package com.pushpender.auth.dtos.UserDto;


import com.pushpender.auth.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReqDto {
    private String email;
    private String password;
    private User.Role role;
}

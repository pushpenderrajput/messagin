package com.pushpender.user.dtos.UserDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String countryCode;
    private Boolean isAdmin;
    private Boolean isActive;

}

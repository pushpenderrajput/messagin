package com.pushpender.user.dtos.UserDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileReqDto {
    private String fullName;
    private String email;
    private String phone;
    private String countryCode;
    private Boolean isAdmin;
    private Boolean isActive;
}

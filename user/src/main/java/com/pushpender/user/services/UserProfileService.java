package com.pushpender.user.services;

import com.pushpender.user.dtos.UserProfileReqDto;
import com.pushpender.user.dtos.UserProfileResDto;
import com.pushpender.user.entities.UserProfile;
import com.pushpender.user.repositories.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepo userProfileRepo;
    public UserProfileResDto getOrCreateByEmail(UserProfileReqDto dto){
        var existingProfile = userProfileRepo.findByEmail(dto.getEmail()).orElse(null);
        if(existingProfile == null){
            UserProfile newProfile = new UserProfile();
            newProfile.setEmail(dto.getEmail());
            newProfile.setFullName(dto.getFullName());
            newProfile.setCountryCode(dto.getCountryCode());
            newProfile.setIsAdmin(dto.getIsAdmin());
            newProfile.setPhone(dto.getPhone());
            newProfile.setIsActive(dto.getIsActive());
            UserProfile savedUser = userProfileRepo.save(newProfile);
            UserProfileResDto response = new UserProfileResDto();
            response.setId(savedUser.getId());
            response.setEmail(savedUser.getEmail());
            response.setFullName(savedUser.getFullName());
            response.setPhone(savedUser.getPhone());
            response.setCountryCode(savedUser.getCountryCode());
            response.setIsActive(savedUser.getIsActive());
            response.setIsAdmin(savedUser.getIsAdmin());
            return response;
        }else{
            UserProfileResDto res = new UserProfileResDto();
            res.setId(existingProfile.getId());
            res.setEmail(existingProfile.getEmail());
            res.setFullName(existingProfile.getFullName());
            res.setPhone(existingProfile.getPhone());
            res.setCountryCode(existingProfile.getCountryCode());
            res.setIsActive(existingProfile.getIsActive());
            res.setIsAdmin(existingProfile.getIsAdmin());

            return res;

        }

    }

    public UserProfileResDto findByEmail(String email){
        var existing = userProfileRepo.findByEmail(email).orElse(null);
        if(existing == null){
            throw new RuntimeException("User Not Found with Email");
        }
        UserProfileResDto response = new UserProfileResDto();
        response.setId(existing.getId());
        response.setEmail(existing.getEmail());
        response.setFullName(existing.getFullName());
        response.setPhone(existing.getPhone());
        response.setCountryCode(existing.getCountryCode());
        response.setIsAdmin(existing.getIsAdmin());
        response.setIsActive(existing.getIsActive());
        return response;
    }

}

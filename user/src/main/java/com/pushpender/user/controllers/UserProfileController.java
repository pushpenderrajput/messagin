package com.pushpender.user.controllers;

import com.pushpender.user.dtos.UserProfileReqDto;
import com.pushpender.user.dtos.UserProfileResDto;
import com.pushpender.user.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfileResDto> getOrCreateByEmail(@RequestBody UserProfileReqDto dto){
        var response = userProfileService.getOrCreateByEmail(dto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{email}")
    public ResponseEntity<UserProfileResDto> getByEmail(@PathVariable String email){
        var response = userProfileService.findByEmail(email);
        return ResponseEntity.ok(response);

    }

}

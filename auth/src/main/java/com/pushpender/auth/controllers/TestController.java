package com.pushpender.auth.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequiredArgsConstructor
public class TestController {





    @GetMapping("/testing")
    public ResponseEntity<Void> testing() {

        return ResponseEntity.ok().build();
    }

}

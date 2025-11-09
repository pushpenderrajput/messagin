package com.pushpender.user.controllers;

import com.pushpender.user.dtos.SenderIdDtos.CreateSenderIdRequest;
import com.pushpender.user.dtos.SenderIdDtos.SenderIdDto;
import com.pushpender.user.entities.SenderId;
import com.pushpender.user.services.SenderIdService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/sender-ids")
public class SenderIdController {

    @Autowired
    private SenderIdService service;


    @PostMapping
    public ResponseEntity<SenderIdDto> createSenderId(
            @RequestBody CreateSenderIdRequest req,
            HttpServletRequest request) {

        String email = (String) request.getAttribute("email");
        var response = service.createSenderId(email, req);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<SenderIdDto>> listSenderIds(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        return ResponseEntity.ok(service.listSenderIds(email));
    }


    @GetMapping("/admin")
    public ResponseEntity<List<SenderIdDto>> listAll() {
        return ResponseEntity.ok(service.listAllSenderIds());
    }


    @PutMapping("/admin/{id}/{status}")
    public ResponseEntity<SenderIdDto> updateStatus(
            @PathVariable Long id,
            @PathVariable SenderId.Status status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }
}

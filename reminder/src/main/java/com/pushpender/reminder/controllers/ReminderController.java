package com.pushpender.reminder.controllers;

import com.pushpender.reminder.dtos.reminderDtos.CreateReminderRequestDto;
import com.pushpender.reminder.dtos.reminderDtos.ReminderResponseDto;
import com.pushpender.reminder.services.ReminderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;


    @PostMapping
    public ResponseEntity<List<ReminderResponseDto>> createReminders(
            @RequestBody CreateReminderRequestDto req,
            HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        String token = request.getHeader("Authorization").substring(7);
        var response = reminderService.createReminder(email, token, req);
        return ResponseEntity.ok(response);
    }



    @GetMapping
    public ResponseEntity<List<ReminderResponseDto>> getUserReminders(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        var response = reminderService.listUserReminders(email);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReminderResponseDto> cancelReminder(
            @PathVariable Long id,
            HttpServletRequest request) {

        String email = (String) request.getAttribute("email");
        var response = reminderService.cancelReminder(email, id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/admin")
    public ResponseEntity<List<ReminderResponseDto>> getAllReminders() {
        var response = reminderService.listAllReminders();
        return ResponseEntity.ok(response);
    }
}

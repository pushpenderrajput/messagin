package com.pushpender.reminder.services;

import com.pushpender.reminder.clients.UserClient;
import com.pushpender.reminder.dtos.reminderDtos.CreateReminderRequestDto;
import com.pushpender.reminder.dtos.reminderDtos.ReminderResponseDto;
import com.pushpender.reminder.entities.Reminder;
import com.pushpender.reminder.repositories.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserClient userClient;

    public ReminderResponseDto createReminder(String email, String token, CreateReminderRequestDto req) {

        if (req.getScheduledAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Scheduled time must be in the future");
        }

        Map<String, Object> sender = userClient.getSenderById(req.getSenderId(), token);
        Map<String, Object> template = userClient.getTemplateById(req.getTemplateId(), token);

        if (sender == null || !sender.get("createdBy").equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid or unauthorized Sender ID");
        }

        if (!"APPROVED".equals(sender.get("status"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender ID not approved");
        }

        if (template == null || !template.get("createdBy").equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid or unauthorized Template");
        }

        if (!"APPROVED".equals(template.get("status"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Template not approved");
        }

        String content = (String) template.get("content");
        if (req.getVariables() != null) {
            for (Map.Entry<String, String> entry : req.getVariables().entrySet()) {
                content = content.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }

        Reminder reminder = Reminder.builder()
                .title(req.getTitle())
                .recipientName(req.getRecipientName())
                .recipientPhone(req.getRecipientPhone())
                .message(content)
                .senderId(req.getSenderId())
                .templateId(req.getTemplateId())
                .scheduledAt(req.getScheduledAt())
                .status(Reminder.Status.SCHEDULED)
                .createdBy(email)
                .build();

        Reminder saved = reminderRepository.save(reminder);

        return ReminderResponseDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .recipientName(saved.getRecipientName())
                .recipientPhone(saved.getRecipientPhone())
                .message(saved.getMessage())
                .status(saved.getStatus())
                .scheduledAt(saved.getScheduledAt())
                .createdBy(saved.getCreatedBy())
                .build();
    }

    public List<ReminderResponseDto> listUserReminders(String email) {
        return reminderRepository.findByCreatedBy(email).stream()
                .map(r -> ReminderResponseDto.builder()
                        .id(r.getId())
                        .title(r.getTitle())
                        .recipientName(r.getRecipientName())
                        .recipientPhone(r.getRecipientPhone())
                        .message(r.getMessage())
                        .status(r.getStatus())
                        .scheduledAt(r.getScheduledAt())
                        .createdBy(r.getCreatedBy())
                        .build())
                .toList();
    }

    public ReminderResponseDto cancelReminder(String email, Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reminder not found"));

        if (!reminder.getCreatedBy().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot cancel reminder of another user");
        }

        if (reminder.getStatus() != Reminder.Status.SCHEDULED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only scheduled reminders can be cancelled");
        }

        reminder.setStatus(Reminder.Status.CANCELLED);
        Reminder saved = reminderRepository.save(reminder);

        return ReminderResponseDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .message(saved.getMessage())
                .status(saved.getStatus())
                .scheduledAt(saved.getScheduledAt())
                .createdBy(saved.getCreatedBy())
                .build();
    }

    public List<ReminderResponseDto> listAllReminders() {
        return reminderRepository.findAll().stream()
                .map(r -> ReminderResponseDto.builder()
                        .id(r.getId())
                        .title(r.getTitle())
                        .recipientName(r.getRecipientName())
                        .recipientPhone(r.getRecipientPhone())
                        .message(r.getMessage())
                        .status(r.getStatus())
                        .scheduledAt(r.getScheduledAt())
                        .createdBy(r.getCreatedBy())
                        .build())
                .toList();
    }
}

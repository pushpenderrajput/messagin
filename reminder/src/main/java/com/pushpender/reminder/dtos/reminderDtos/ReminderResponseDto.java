package com.pushpender.reminder.dtos.reminderDtos;

import com.pushpender.reminder.entities.Reminder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReminderResponseDto {
    private Long id;
    private String title;
    private String recipientName;
    private String recipientPhone;
    private String message;
    private Reminder.Status status;
    private LocalDateTime scheduledAt;
    private String createdBy;
}

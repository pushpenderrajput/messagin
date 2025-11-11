package com.pushpender.reminder.dtos.reminderDtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReminderRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String recipientName;

    @NotBlank
    private String recipientPhone;

    @NotNull
    private Long senderId;

    @NotNull
    private Long templateId;

    private Map<String, String> variables;

    @NotNull
    @Future(message = "Scheduled time must be in the future")
    private LocalDateTime scheduledAt;


}

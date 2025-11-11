package com.pushpender.reminder.dtos.reminderDtos;
import com.pushpender.reminder.dtos.contactDto.ContactDto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReminderRequestDto {
    @NotBlank
    private String title;

    @NotNull
    private Long senderId;

    @NotNull
    private Long templateId;

    @NotNull
    @Future(message = "Scheduled time must be in the future")
    private LocalDateTime scheduledAt;

    @NotNull
    private List<ContactDto> contacts;
}

package com.pushpender.reminder.dtos.contactDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDto {
    @NotBlank
    private String recipientName;

    @NotBlank
    private String recipientPhone;

    private Map<String, String> variables;
}

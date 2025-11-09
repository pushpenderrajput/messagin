package com.pushpender.user.dtos.TemplateDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateRequestDto {
    private String title;
    private String content;
    private Long senderId;
}

package com.pushpender.user.dtos.TemplateDtos;

import com.pushpender.user.entities.Template;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateResponseDto {
    private Long id;
    private String title;
    private String content;
    private Template.Status status;
    private String senderName;
    private String createdBy;
}

package com.pushpender.user.dtos;

import com.pushpender.user.entities.SenderId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SenderIdDto {
    private Long id;
    private String name;
    private SenderId.Status status;
    private String createdBy;
}

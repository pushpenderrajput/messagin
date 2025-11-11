package com.pushpender.reminder.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String recipientName;

    @Column(nullable = false, length = 20)
    private String recipientPhone;

    @Column(nullable = false, length = 1600)
    private String message;

    @Column(nullable = false)
    private Long senderId;

    @Column(nullable = false)
    private Long templateId;

    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.SCHEDULED;

    @Column(nullable = false)
    private String createdBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String twilioSid;
    private String deliveryStatus;
    private LocalDateTime lastDeliveryUpdate;
    public enum Status {
        SCHEDULED,
        SENT,
        FAILED,
        CANCELLED
    }
}

package com.pushpender.reminder.repositories;

import com.pushpender.reminder.entities.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByCreatedBy(String email);
    List<Reminder> findByStatusAndScheduledAtBefore(Reminder.Status status, LocalDateTime time);
    List<Reminder> findByStatusAndScheduledAtBefore(Reminder.Status status, LocalDateTime time, Pageable pageable);
    List<Reminder> findByStatus(Reminder.Status status);

}

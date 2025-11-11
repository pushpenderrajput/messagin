package com.pushpender.reminder.scheduler;

import com.pushpender.reminder.entities.Reminder;
import com.pushpender.reminder.repositories.ReminderRepository;
import com.pushpender.reminder.services.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class ReminderScheduler {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private SmsService smsService;

    @Scheduled(fixedRate = 60000)
    public void processDueReminders() {
        log.info("⏰ Scheduler triggered at {}", LocalDateTime.now());

        List<Reminder> dueReminders = reminderRepository
                .findByStatusAndScheduledAtBefore(Reminder.Status.SCHEDULED, LocalDateTime.now());

        if (dueReminders.isEmpty()) {
            log.info("No reminders to send at this time.");
            return;
        }

        log.info("Found {} due reminders.", dueReminders.size());

        for (Reminder reminder : dueReminders) {
            boolean sent = smsService.sendSms(reminder);

            if (sent) {
                reminder.setStatus(Reminder.Status.SENT);
                log.info("✅ Reminder {} sent successfully to {}", reminder.getId(), reminder.getRecipientPhone());
            } else {
                reminder.setStatus(Reminder.Status.FAILED);
                log.warn("⚠️ Reminder {} failed to send to {}", reminder.getId(), reminder.getRecipientPhone());
            }

            reminderRepository.save(reminder);
        }
    }
}

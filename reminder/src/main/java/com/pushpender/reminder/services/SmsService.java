package com.pushpender.reminder.services;

import com.pushpender.reminder.config.TwilioConfig;
import com.pushpender.reminder.entities.Reminder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsService {

    @Autowired
    private TwilioConfig twilioConfig;

    @Autowired
    private TwilioSmsService twilioSmsService;

    public boolean sendSms(Reminder reminder) {
        if (twilioConfig.isEnabled()) {
            return twilioSmsService.sendSms(reminder);
        } else {
            log.info("📩 Simulated SMS: To={} | Msg={}", reminder.getRecipientPhone(), reminder.getMessage());
            return true;
        }
    }
}

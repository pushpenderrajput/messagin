package com.pushpender.reminder.services;

import com.pushpender.reminder.config.TwilioConfig;
import com.pushpender.reminder.entities.Reminder;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class TwilioSmsService {

    @Autowired
    private TwilioConfig twilioConfig;

    public boolean sendSms(Reminder reminder) {
        if (!twilioConfig.isEnabled()) {
            log.info("⚙️ Twilio disabled — simulation mode active.");
            return true;
        }

        try {
            Message message = Message.creator(
                            new PhoneNumber(reminder.getRecipientPhone()),
                            new PhoneNumber(twilioConfig.getFromNumber()),
                            reminder.getMessage()
                    )
                    // Optional: webhook URL for delivery reports
                    .setStatusCallback("https://your-domain.com/api/reminders/delivery-callback")
                    .create();

            reminder.setTwilioSid(message.getSid());
            reminder.setDeliveryStatus(message.getStatus().toString());
            reminder.setLastDeliveryUpdate(LocalDateTime.now());

            log.info("📤 Sent SMS via Twilio: SID={}, To={}, Status={}",
                    message.getSid(), reminder.getRecipientPhone(), message.getStatus());
            return true;

        } catch (Exception e) {
            log.error("❌ Twilio SMS send failed to {} — {}", reminder.getRecipientPhone(), e.getMessage());
            reminder.setDeliveryStatus("FAILED");
            reminder.setLastDeliveryUpdate(LocalDateTime.now());
            return false;
        }
    }
}

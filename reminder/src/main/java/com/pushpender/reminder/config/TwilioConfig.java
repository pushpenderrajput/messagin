package com.pushpender.reminder.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TwilioConfig {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.fromNumber}")
    private String fromNumber;

    @Value("${twilio.enabled:true}")
    private boolean enabled;

    @PostConstruct
    public void initTwilio() {
        if (enabled) {
            Twilio.init(accountSid, authToken);
            System.out.println("✅ Twilio initialized successfully.");
        } else {
            System.out.println("⚙️ Twilio disabled — running in simulation mode.");
        }
    }
}

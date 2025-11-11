package com.pushpender.reminder.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class AuthClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${auth.service.url}")
    private String authServiceUrl;

    public Map<String, Object> validateToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    authServiceUrl + "/api/auth/validate",
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            return response.getBody();
        } catch (Exception e) {
            return null;
        }
    }
}

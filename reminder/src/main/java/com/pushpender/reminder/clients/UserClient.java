package com.pushpender.reminder.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class UserClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${user.service.url}")
    private String userServiceUrl;

    public Map<String, Object> getSenderById(Long id, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    userServiceUrl + "/api/user/sender-ids/" + id,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );
            return response.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Object> getTemplateById(Long id, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    userServiceUrl + "/api/user/templates/" + id,
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

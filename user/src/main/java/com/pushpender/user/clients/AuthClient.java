package com.pushpender.user.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthClient {

    private final RestTemplate restTemplate;
    @Value("${auth.service.url}")
    private String authServiceUrl;

    public AuthClient() {
        this.restTemplate = new RestTemplate();
    }

    public boolean validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    authServiceUrl + "/api/auth/validate",
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception ex) {
            return false;
        }
    }
}

package com.pushpender.user.security;

import com.pushpender.user.clients.AuthClient;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthInterceptor implements Filter {

    private final AuthClient authClient;

    public AuthInterceptor(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String authHeader = req.getHeader("Authorization");

        // Allow open endpoints (if any)
        if (req.getRequestURI().startsWith("/actuator") || req.getMethod().equalsIgnoreCase("OPTIONS")) {
            chain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);
        boolean valid = authClient.validateToken(token);

        if (!valid) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        chain.doFilter(request, response);
    }
}

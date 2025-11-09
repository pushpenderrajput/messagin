package com.pushpender.user.controllers;

import com.pushpender.user.dtos.TemplateDtos.TemplateRequestDto;
import com.pushpender.user.dtos.TemplateDtos.TemplateResponseDto;
import com.pushpender.user.entities.Template;
import com.pushpender.user.services.TemplateService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/templates")
public class TemplateController {

    @Autowired
    private TemplateService service;



    @PostMapping
    public ResponseEntity<TemplateResponseDto> createTemplate(
            @RequestBody TemplateRequestDto req,
            HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        return ResponseEntity.ok(service.createTemplate(email, req));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<TemplateResponseDto> editTemplate( @PathVariable Long id ,
            @RequestBody TemplateRequestDto req,
            HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        return ResponseEntity.ok(service.editTemplate(email, id, req));
    }

    @GetMapping
    public ResponseEntity<List<TemplateResponseDto>> listTemplates(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        return ResponseEntity.ok(service.listUserTemplates(email));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<TemplateResponseDto>> listAllTemplates() {
        return ResponseEntity.ok(service.listAllTemplates());
    }

    @PutMapping("/admin/{id}/{status}")
    public ResponseEntity<TemplateResponseDto> updateStatus(
            @PathVariable Long id,
            @PathVariable Template.Status status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }
}

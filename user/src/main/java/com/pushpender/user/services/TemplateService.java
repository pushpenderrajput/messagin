package com.pushpender.user.services;

import com.pushpender.user.dtos.TemplateDtos.TemplateRequestDto;
import com.pushpender.user.dtos.TemplateDtos.TemplateResponseDto;
import com.pushpender.user.entities.SenderId;
import com.pushpender.user.entities.Template;
import com.pushpender.user.repositories.SenderIdRepository;
import com.pushpender.user.repositories.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TemplateService {
    @Autowired
    private TemplateRepository templateRepo;
    @Autowired
    private SenderIdRepository senderRepo;



    public TemplateResponseDto createTemplate(String email, TemplateRequestDto req) {
        SenderId sender = senderRepo.findById(req.getSenderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender ID not found"));

        if (!sender.getCreatedBy().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not own this Sender ID");
        }

        if (sender.getStatus() != SenderId.Status.APPROVED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender ID is not approved");
        }

        Template template = Template.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .senderId(sender)
                .status(Template.Status.PENDING)
                .build();

        Template saved = templateRepo.save(template);
        return TemplateResponseDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .status(saved.getStatus())
                .senderName(saved.getSenderId().getName())
                .createdBy(saved.getSenderId().getCreatedBy())
                .build();
    }

    public TemplateResponseDto editTemplate(String email, Long id, TemplateRequestDto req) {
        Template existing = templateRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template not found"));

        if (!existing.getSenderId().getCreatedBy().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not own this template");
        }

        existing.setTitle(req.getTitle());
        existing.setContent(req.getContent());
        existing.setStatus(Template.Status.PENDING);

        Template saved = templateRepo.save(existing);
        return TemplateResponseDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .status(saved.getStatus())
                .senderName(saved.getSenderId().getName())
                .createdBy(saved.getSenderId().getCreatedBy())
                .build();
    }

    public TemplateResponseDto getById(Long id) {
        var template = templateRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template not found"));

        return TemplateResponseDto.builder()
                .id(template.getId())
                .title(template.getTitle())
                .content(template.getContent())
                .status(template.getStatus())
                .senderName(template.getSenderId().getName())
                .createdBy(template.getSenderId().getCreatedBy())
                .build();
    }


    public List<TemplateResponseDto> listUserTemplates(String email) {
        return templateRepo.findBySenderId_CreatedBy(email)
                .stream().map(t->{
                    return TemplateResponseDto.builder()
                            .id(t.getId())
                            .title(t.getTitle())
                            .content(t.getContent())
                            .status(t.getStatus())
                            .senderName(t.getSenderId().getName())
                            .createdBy(t.getSenderId().getCreatedBy())
                            .build();

                }).toList();
    }

    public List<TemplateResponseDto> listAllTemplates() {
        return templateRepo.findAll().stream().map(t->{
            return TemplateResponseDto.builder()
                    .id(t.getId())
                    .title(t.getTitle())
                    .content(t.getContent())
                    .status(t.getStatus())
                    .senderName(t.getSenderId().getName())
                    .createdBy(t.getSenderId().getCreatedBy())
                    .build();

        }).toList();
    }

    public TemplateResponseDto updateStatus(Long id, Template.Status status) {
        Template template = templateRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template not found"));
        template.setStatus(status);
        Template saved = templateRepo.save(template);
        return TemplateResponseDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .status(saved.getStatus())
                .senderName(saved.getSenderId().getName())
                .createdBy(saved.getSenderId().getCreatedBy())
                .build();
    }
}

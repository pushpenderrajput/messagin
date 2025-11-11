package com.pushpender.user.services;


import com.pushpender.user.dtos.SenderIdDtos.CreateSenderIdRequest;
import com.pushpender.user.dtos.SenderIdDtos.SenderIdDto;
import com.pushpender.user.entities.SenderId;
import com.pushpender.user.repositories.SenderIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SenderIdService {

    @Autowired
    private SenderIdRepository repo;

    public SenderIdDto createSenderId(String email, CreateSenderIdRequest dto){
        if(repo.findByCreatedBy(email).stream().anyMatch(s->s.getName().equalsIgnoreCase(dto.getName()))){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Sender ID already exists");
        }
        SenderId sender = SenderId.builder()
                .name(dto.getName())
                .status(SenderId.Status.PENDING)
                .createdBy(email)
                .build();
        SenderId saved = repo.save(sender);
        return SenderIdDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .createdBy(saved.getCreatedBy())
                .status(saved.getStatus())
                .build();
    }
    public SenderIdDto getById(Long id) {
        var sender = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender ID not found"));

        return SenderIdDto.builder()
                .id(sender.getId())
                .name(sender.getName())
                .status(sender.getStatus())
                .createdBy(sender.getCreatedBy())
                .build();
    }


    public List<SenderIdDto> listSenderIds(String email){
        return repo.findByCreatedBy(email).stream()
                .map(sender->{return SenderIdDto.builder()
                        .id(sender.getId())
                        .name(sender.getName())
                        .status(sender.getStatus())
                        .createdBy(sender.getCreatedBy())
                        .build();
                }).toList();
    }

    public List<SenderIdDto> listAllSenderIds(){
        return repo.findAll().stream()
                .map(sender->{return SenderIdDto.builder()
                        .id(sender.getId())
                        .name(sender.getName())
                        .status(sender.getStatus())
                        .createdBy(sender.getCreatedBy())
                        .build();
                }).toList();
    }

    public SenderIdDto updateStatus(Long id, SenderId.Status status) {
        SenderId sender = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender ID not found"));
        sender.setStatus(status);
        SenderId saved = repo.save(sender);
        return SenderIdDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .createdBy(saved.getCreatedBy())
                .status(saved.getStatus())
                .build();
    }

}

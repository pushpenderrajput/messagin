package com.pushpender.user.repositories;

import com.pushpender.user.entities.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findBySenderId_CreatedBy(String email);
}

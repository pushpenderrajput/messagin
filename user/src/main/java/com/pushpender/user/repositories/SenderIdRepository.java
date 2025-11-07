package com.pushpender.user.repositories;

import com.pushpender.user.entities.SenderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SenderIdRepository extends JpaRepository<SenderId, Long> {
    List<SenderId> findByCreatedBy(String email);
}

package com.pushpender.user.repositories;


import com.pushpender.user.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile,Long> {

    Optional<UserProfile> findByEmail(String email);
}

package com.pushpender.user.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column
    private String fullName;

    @Column
    private String phone;

    @Column
    private String countryCode;

    @Column
    private Boolean isActive = true;
    
    @Column
    private Boolean isAdmin = false;





}

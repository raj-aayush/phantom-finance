package com.rajaayush.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class AppUser {

    @Id
    @UuidGenerator
    private UUID id;

    private String username;

    private String password;

    private String roles; // TODO: This should be a list

    @CreationTimestamp
    private LocalDateTime createdTs;

    @UpdateTimestamp
    private LocalDateTime updatedTs;
}

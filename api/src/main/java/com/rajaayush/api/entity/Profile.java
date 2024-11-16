package com.rajaayush.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String email;

    @CreationTimestamp
    @Column(name = "created_ts", nullable = false)
    private LocalDateTime createdTs;
}


package com.rajaayush.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Customer {

    @Id
    @UuidGenerator
    private UUID id;

    private String firstName;

    private String email;

    @CreationTimestamp
    private LocalDateTime createdTs;
}


package com.rajaayush.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Account {
    @Id
    @UuidGenerator
    public UUID id;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    public Customer owner;

    public double balance;

    @CreationTimestamp
    private LocalDateTime createdTs;

    @UpdateTimestamp
    private LocalDateTime updatedTs;
}

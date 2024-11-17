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
public class Transaction {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name="sender_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name="receiver_id")
    private Account receiver;

    private double amount;

    @CreationTimestamp
    private LocalDateTime createdTs;

    @UpdateTimestamp
    private LocalDateTime updatedTs;
}

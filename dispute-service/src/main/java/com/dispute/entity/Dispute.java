package com.dispute.entity;

import com.dispute.enums.DisputeStatus;
import com.dispute.enums.DisputeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "disputes")
public class Dispute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,updatable = false)
    private String disputeId;

    private String transactionId;

    private String reason;

    private String description;

    @Enumerated(EnumType.STRING)
    private DisputeStatus disputeStatus;

    @Enumerated(EnumType.STRING)
    private DisputeType disputeType;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private long version;
/*
    public Dispute(Long id,
                   String transactionId,
                   String reason,
                   String description,
                   DisputeStatus DisputeStatus,
                   LocalDateTime createdAt,
                   int userId,String title,
                   LocalDateTime updatedAt,
                   DisputeType disputeType) {

        this.id = id;
        this.transactionId = transactionId;
        this.reason = reason;
        this.description = description;
        this.disputeStatus = disputeStatus;
        this.createdAt = createdAt;
        this.customerId = userId;
        this.title=title;
        this.updatedAt=updatedAt;
        this.disputeType=disputeType;
    }

 */

    @PrePersist
    public void prePersist() {

        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;

        if (this.disputeStatus == null) {
            this.disputeStatus = DisputeStatus.OPEN;
        }

        if (this.disputeId == null) {
            String shortId = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 12)
                    .toUpperCase();

            this.disputeId = "DSP-" + shortId;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
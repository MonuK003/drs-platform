package com.example.transactionservice.Entity;

import com.example.transactionservice.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_123")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private Long customerId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Status status;


    @PrePersist
    public void prePersist(){
        this.amount = BigDecimal.valueOf(
                ThreadLocalRandom.current().nextDouble(100, 10000)
        ).setScale(2, RoundingMode.HALF_UP);

    }


}

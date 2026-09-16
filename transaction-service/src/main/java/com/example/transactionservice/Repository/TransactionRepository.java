package com.example.transactionservice.Repository;

import com.example.transactionservice.Entity.Transaction;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository  extends JpaRepository<Transaction,Long> {

    Optional<Transaction> findByTransactionId(String transactionId);
}
